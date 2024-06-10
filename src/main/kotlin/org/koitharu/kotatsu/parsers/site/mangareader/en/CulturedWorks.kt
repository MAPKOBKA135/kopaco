package org.koitharu.kotatsu.parsers.site.mangareader.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.mangareader.MangaReaderParser

@MangaSourceParser("CULTUREDWORKS", "CulturedWorks", "en")
internal class CulturedWorks(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaSource.CULTUREDWORKS, "culturedworks.com", pageSize = 20, searchPageSize = 10) {
	override val selectMangaList = ".listupd .bs .bsx"
}
