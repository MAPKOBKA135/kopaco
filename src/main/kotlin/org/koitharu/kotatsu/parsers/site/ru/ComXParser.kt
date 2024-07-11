package org.koitharu.kotatsu.parsers.site.ru

import org.koitharu.kotatsu.parsers.*
import org.koitharu.kotatsu.parsers.config.ConfigKey
import org.koitharu.kotatsu.parsers.model.*
import org.koitharu.kotatsu.parsers.util.*
import java.text.SimpleDateFormat
import java.util.*

@MangaSourceParser("COMX", "Com-X", "ru")
internal class ComXParser(context: MangaLoaderContext) :
	PagedMangaParser(context, MangaSource.COMX, 30) {

	override val configKeyDomain = ConfigKey.Domain("com-x.life")

	override val availableSortOrders: Set<SortOrder> = EnumSet.of(
		SortOrder.ALPHABETICAL,
		SortOrder.RATING,
		SortOrder.POPULARITY,
		SortOrder.UPDATED
	)

	override val availableStates: Set<MangaState> = EnumSet.of(
		MangaState.ONGOING,
		MangaState.FINISHED
	)

	override val isMultipleTagsSupported = false

	override suspend fun getListPage(page: Int, filter: MangaListFilter?): List<Manga> {
		val url = buildString {
			append("https://")
			append(domain)
			when (filter) {
				is MangaListFilter.Search -> {
					append("/search/")
					append(filter.query.urlEncoded())
					append("/page/")
					append(page.toString())
				}

				is MangaListFilter.Advanced -> {
					append("/ComicList/")
					if (filter.states.isNotEmpty()) {
						filter.states.oneOrThrowIfMany()?.let {
							append(
								when (it) {
									MangaState.ONGOING -> "ongoing"
									MangaState.FINISHED -> "completed"
									else -> "0"
								},
							)
						}
					} else {
						append("st=34/")
					}

					append(
						when (filter.sortOrder) {
							SortOrder.POPULARITY -> "popular/"
							SortOrder.UPDATED -> "updated/"
							SortOrder.RATING -> "rating/"
							else -> ""
						},
					)
					append("page/")
					append(page.toString())
				}

				null -> {
					append("/ComicList/page/")
					append(page.toString())
				}
			}
		}

		val doc = webClient.httpGet(url).parseHtml()
		val root = doc.body().selectFirst("div.dle-content") ?: return emptyList()
		return root.select("div.readed").mapNotNull { li ->
			val a = li.selectFirst("a.readed__img")
			val href = a?.attrAsRelativeUrlOrNull("href") ?: return@mapNotNull null
			val title = li.selectFirst("h3.readed__title")?.text() ?: return@mapNotNull null
			val views = li.select("ul.readed__info > li")
			val status = views.firstNotNullOfOrNull { it.ownText().takeIf { x -> x.startsWith("Последний выпуск:") } }
				?.substringAfter(':')?.trim()?.lowercase(Locale.ROOT)
			Manga(
				id = generateUid(href),
				title = title,
				coverUrl = a.selectFirst("img")?.absUrl("src").orEmpty(),
				source = source,
				altTitle = null,
				rating = li.selectFirst("p.score")?.selectFirst("b")
					?.ownText()?.toFloatOrNull()?.div(5f) ?: RATING_UNKNOWN,
				author = views.firstNotNullOfOrNull { it.text().takeIf { x -> x.startsWith("Автор:") } }
					?.substringAfter(':')
					?.trim(),
				state = when (status) {
					"ongoing" -> MangaState.ONGOING
					"completed" -> MangaState.FINISHED
					else -> null
				},
				tags = li.selectFirst("span.genres")?.select("a")?.mapNotNullToSet tags@{ x ->
					MangaTag(
						title = x.text(),
						key = x.attr("href").substringAfter("/genre/"),
						source = source,
					)
				}.orEmpty(),
				url = href,
				isNsfw = false,
				publicUrl = href.toAbsoluteUrl(a.host ?: domain),
			)
		}
	}

	override suspend fun getDetails(manga: Manga): Manga {
		val doc = webClient.httpGet(manga.url.toAbsoluteUrl(domain)).parseHtml()
		val root = doc.body().selectFirstOrThrow("section.main")
			.selectFirstOrThrow("div.article_content")
		val info = root.selectFirst("div.detail_info")?.selectFirst("ul")
		val chaptersList = root.selectFirst("div.chapter_content")
			?.selectFirst("ul.chapter_list")?.select("li")?.asReversed()
		val dateFormat = SimpleDateFormat("MMM dd,yyyy", Locale.US)

		return manga.copy(
			tags = manga.tags + info?.select("li")?.find { x ->
				x.selectFirst("b")?.ownText() == "Жанр(ы):"
			}?.select("a")?.mapNotNull { a ->
				MangaTag(
					title = a.text(),
					key = a.attr("href").substringAfter("/genre/"),
					source = source,
				)
			}.orEmpty(),
			description = info?.getElementById("show")?.ownText(),
			chapters = chaptersList?.mapIndexed { i, li ->
				val href = li.selectFirst("a")?.attrAsRelativeUrlOrNull("href")
					?: return@mapIndexed null
				val name = li.select("span")
					.filter { x -> x.className().isEmpty() }
					.joinToString(" - ") { it.text() }.trim()
				MangaChapter(
					id = generateUid(href),
					name = name.ifEmpty { "${manga.title} - ${i + 1}" },
					number = i + 1f,
					volume = 0,
					url = href,
					scanlator = null,
					uploadDate = parseChapterDate(dateFormat, li.selectFirst("span.time")?.text())?.time ?: 0,
					branch = null,
					source = source,
				)
			}?.filterNotNull() ?: emptyList()
		)
	}

	override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
		val fullUrl = chapter.url.toAbsoluteUrl(domain)
		val doc = webClient.httpGet(fullUrl).parseHtml()
		val root = doc.body().selectFirstOrThrow("div.page_select")
		val isManga = root.select("select")

		if (isManga.isEmpty()) { // Webtoon
			val imgElements = doc.select("div#viewer.read_img img.image")
			return imgElements.map {
				val href = it.attr("src")
				MangaPage(
					id = generateUid(href),
					url = href,
					preview = null,
					source = source,
				)

			}
		} else { // Manga
			return isManga.select("option").mapNotNull {
				val href = it.attrAsRelativeUrlOrNull("value")
				if (href == null || href.endsWith("featured.html")) {
					return@mapNotNull null
				}
				MangaPage(
					id = generateUid(href),
					url = href,
					preview = null,
					source = source,
				)
			}
		}
	}

	override suspend fun getPageUrl(page: MangaPage): String {
		if (page.url.startsWith("//")) { // Webtoon
			return page.url.toAbsoluteUrl(domain)
		}

		val doc = webClient.httpGet(page.url.toAbsoluteUrl(domain)).parseHtml()
		return doc.requireElementById("image").attrAsAbsoluteUrl("src")
	}

	override suspend fun getAvailableTags(): Set<MangaTag> {
		val doc = webClient.httpGet("/directory/".toAbsoluteUrl(domain)).parseHtml()
		val root = doc.body().selectFirst("aside.right")
			?.getElementsContainingOwnText("Genres")
			?.first()
			?.nextElementSibling() ?: doc.parseFailed("Root not found")
		return root.select("li").mapNotNullToSet { li ->
			val a = li.selectFirst("a") ?: return@mapNotNullToSet null
			val key = a.attr("href").substringAfter("/directory/0-").substringBefore("-0-")
			MangaTag(
				source = source,
				key = key,
				title = a.text(),
			)
		}
	}

	private fun parseChapterDate(dateFormat: SimpleDateFormat, dateString: String?): Date? {
		return try {
			dateString?.let { dateFormat.parse(it) }
		} catch (e: Exception) {
			null
	}
}
}

