package org.koitharu.kotatsu.parsers.site.madara.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("MANHUAES", "ManhuaEs", "en")
internal class Manhuaes(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANHUAES, "manhuaes.com") {
	override val postReq = true
}
