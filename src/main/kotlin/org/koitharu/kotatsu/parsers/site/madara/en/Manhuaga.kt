package org.koitharu.kotatsu.parsers.site.madara.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("MANHUAGA", "ManhuaGa", "en")
internal class Manhuaga(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANHUAGA, "manhuaga.com")
