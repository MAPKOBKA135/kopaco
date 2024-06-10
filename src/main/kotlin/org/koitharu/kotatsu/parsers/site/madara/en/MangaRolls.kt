package org.koitharu.kotatsu.parsers.site.madara.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGAROLLS", "MangaRolls", "en")
internal class MangaRolls(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANGAROLLS, "mangarolls.net")
