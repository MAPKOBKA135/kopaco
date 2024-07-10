package org.koitharu.kotatsu.parsers.site.madara.pt

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("NINJASCAN", "NinjaComics", "pt")
internal class NinjaScan(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.NINJASCAN, "ninjacomics.xyz") {
	override val datePattern: String = "dd 'de' MMMMM 'de' yyyy"
}
