package org.koitharu.kotatsu.parsers.site.madara.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.ContentType
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("PORNCOMIXONLINE", "PornComix Online", "en", ContentType.HENTAI)
internal class PornComixOnline(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.PORNCOMIXONLINE, "www.porncomixonline.net") {
	override val listUrl = "m-comic/"
}
