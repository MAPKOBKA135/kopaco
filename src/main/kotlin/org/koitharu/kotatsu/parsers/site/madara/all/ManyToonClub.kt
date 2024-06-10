package org.koitharu.kotatsu.parsers.site.madara.all

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.ContentType
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser
import java.util.Locale

@MangaSourceParser("MANYTOON_CLUB", "ManyToon.club", "", ContentType.HENTAI)
internal class ManyToonClub(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANYTOON_CLUB, "manytoon.club") {
	override val postReq = true
	override val listUrl = "manhwa-raw/"
	override val tagPrefix = "manhwa-raw-genre/"
	override val sourceLocale: Locale = Locale.ENGLISH
}
