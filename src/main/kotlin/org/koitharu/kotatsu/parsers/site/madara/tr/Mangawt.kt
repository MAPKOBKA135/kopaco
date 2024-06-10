package org.koitharu.kotatsu.parsers.site.madara.tr

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAWT", "MangaWt", "tr")
internal class Mangawt(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANGAWT, "mangawt.com")
