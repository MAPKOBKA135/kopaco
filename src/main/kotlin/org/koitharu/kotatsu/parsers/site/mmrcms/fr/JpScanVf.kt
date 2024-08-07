package org.koitharu.kotatsu.parsers.site.mmrcms.fr

import org.koitharu.kotatsu.parsers.Broken
import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.mmrcms.MmrcmsParser
import java.util.*

@Broken
@MangaSourceParser("JPSCANVF", "LireScanVf.com", "fr")
internal class JpScanVf(context: MangaLoaderContext) :
	MmrcmsParser(context, MangaSource.JPSCANVF, "lirescanvf.com") {
	override val sourceLocale: Locale = Locale.ENGLISH
}
