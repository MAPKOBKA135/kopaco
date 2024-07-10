package org.koitharu.kotatsu.parsers.site.madara.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGATX_TO", "MangaTx.to", "en")
internal class MangaTxTo(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANGATX_TO, "mangatx.to", 10) {
	override val tagPrefix = "manhua-genre/"
}
