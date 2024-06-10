package org.koitharu.kotatsu.parsers.site.foolslide.fr

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.foolslide.FoolSlideParser

@MangaSourceParser("HNISCANTRAD", "HniScantrad", "fr")
internal class HniScantrad(context: MangaLoaderContext) :
	FoolSlideParser(context, MangaSource.HNISCANTRAD, "hni-scantrad.com") {
	override val pagination = false
	override val searchUrl = "lel/search/"
	override val listUrl = "lel/directory/"
}
