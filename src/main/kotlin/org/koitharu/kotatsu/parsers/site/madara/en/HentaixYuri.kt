package org.koitharu.kotatsu.parsers.site.madara.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.ContentType
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("HENTAIXYURI", "Hentai x Yuri", "en", ContentType.HENTAI)
internal class HentaixYuri(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.HENTAIXYURI, "hentaixyuri.com", 16) {
	override val postReq = true
}
