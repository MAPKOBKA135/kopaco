package org.koitharu.kotatsu.parsers.site.mmrcms.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.ContentType
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.mmrcms.MmrcmsParser

@MangaSourceParser("READCOMICSONLINE", "ReadComicsOnline", "en", ContentType.COMICS)
internal class ReadComicsOnline(context: MangaLoaderContext) :
	MmrcmsParser(context, MangaSource.READCOMICSONLINE, "readcomicsonline.ru") {
	override val selectState = "dt:contains(Status)"
	override val selectTag = "dt:contains(Categories)"
}
