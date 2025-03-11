package de.bueny.labbayk.ui.screens

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.util.toArabicNumber

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ChapterDetailScreen(
//    modifier: Modifier = Modifier,
//    selectedChapter: State<List<String>?>,
//    function: () -> Unit
//) {
//    val maxLinesPerPage = 15  // Maximal 15 Zeilen pro Seite
//
//    // Funktion zum Aufteilen der Verse in Seiten basierend auf der Zeilenanzahl
//    val pages = remember(selectedChapter.value) {
//        val allVerses = selectedChapter.value ?: emptyList()
//        val pagesList = mutableListOf<List<String>>()
//        var currentPage = mutableListOf<String>()
//        var currentLineCount = 0
//
//        for (verse in allVerses) {
//            val estimatedLines = estimateLineCount(verse)  // Zeilenanzahl für diesen Vers berechnen
//
//            if (currentLineCount + estimatedLines > maxLinesPerPage) {
//                // Falls die max. Zeilenanzahl erreicht ist, erstelle eine neue Seite
//                pagesList.add(currentPage)
//                currentPage = mutableListOf()
//                currentLineCount = 0
//            }
//
//            currentPage.add(verse)
//            currentLineCount += estimatedLines
//        }
//
//        // Letzte Seite hinzufügen, falls noch Verse übrig sind
//        if (currentPage.isNotEmpty()) {
//            pagesList.add(currentPage)
//        }
//
//        pagesList
//    }
//
//    val pagerState = rememberPagerState { pages.size }
//
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//        HorizontalPager(
//            state = pagerState,
//            modifier = modifier.fillMaxSize(),
//        ) { pageIndex ->
//            val currentPage = pages.getOrNull(pageIndex) ?: emptyList()
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                contentAlignment = Alignment.Center // Text vertikal zentrieren
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    currentPage.forEach { verse ->
//                        Text(
//                            text = verse,
//                            fontSize = 24.sp,
//                            textAlign = TextAlign.Center,
//                            lineHeight = 36.sp,
//                            fontWeight = FontWeight.Bold,
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
///**
// * Schätzt die Anzahl der Zeilen, die ein Vers einnimmt, basierend auf der Länge des Textes.
// */
//fun estimateLineCount(text: String): Int {
//    val avgCharsPerLine = 45  // Durchschnittliche Zeichen pro Zeile (je nach Schriftgröße anpassen)
//    return (text.length / avgCharsPerLine.toDouble()).coerceAtLeast(1.0).toInt()
//}

//
//@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
//@Composable
//fun ChapterDetailScreen(
//    modifier: Modifier = Modifier,
//    chapter: State<List<String>?>,
//    onVerseClick: (String) -> Unit
//) {
//    val bismillah = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ"
//
//    // Verse in Zeilen aufteilen, basierend auf der verfügbaren Breite
//    val lines = chapter.value?.flatMap { verse ->
//        splitVerseIntoLines(verse)
//    } ?: emptyList()
//
//    // Zeilen in Seiten aufteilen (max. 15 Zeilen pro Seite)
//    val pages = lines.chunked(15)
//
//    // Initialisiere pagerState mit pageCount aus der Anzahl der Seiten
//    val pagerState = rememberPagerState(
//        initialPage = 0,
//        pageCount = { pages.size }
//    )
//
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//        HorizontalPager(
//            state = pagerState,
//            modifier = modifier.fillMaxSize()
//        ) { page ->
//            val currentPageLines = pages.getOrNull(page) ?: emptyList()
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//
//                Text(
//                    text = bismillah,
//                    style = customTextStyle().copy(
//                        fontSize = 36.sp,
//                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 16.dp)
//                )
//
//                // Zeilen innerhalb der Seite anzeigen
//                currentPageLines.forEach { line ->
//                    FlowRow(
//                        horizontalArrangement = Arrangement.spacedBy(4.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        line.forEach { word ->
//                            Text(
//                                text = word,
//                                modifier = Modifier
//                                    .padding(1.dp)
//                                    .clickable { onVerseClick(word) },
//                                style = customTextStyle()
//                            )
//                        }
//                    }
//                }
//
//                // Zeigt das Seitenindex an
//                Box(
//                    modifier = Modifier
//                        .height(30.dp)
//                        .width(14.dp)
//                        .background(
//                            color = Color(0xBE2A4073),
//                            shape = CircleShape
//                        )
//                        .wrapContentSize(Alignment.Center)
//                ) {
//                    Text(
//                        text = (page + 1).toString(),
//                        color = Color.White,
//                        style = customTextStyle().copy(
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.Normal
//                        ),
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            }
//        }
//    }
//}
//
//// Hilfsfunktion für das Aufteilen eines Verses in Zeilen, basierend auf der verfügbaren Breite
//fun splitVerseIntoLines(verse: String): List<List<String>> {
//    val words = verse.split(" ")
//    val lines = mutableListOf<List<String>>()
//    var currentLine = mutableListOf<String>()
//
//    for (word in words) {
//        // Füge das Wort zur aktuellen Zeile hinzu
//        currentLine.add(word)
//        // Die Zeile wird erst hinzugefügt, wenn die nächste Zeile beginnt
//        // oder wenn der Vers zu Ende ist
//    }
//    // Die letzte Zeile hinzufügen
//    if (currentLine.isNotEmpty()) {
//        lines.add(currentLine)
//    }
//
//    return lines
//}

@OptIn(UnstableApi::class)
@Composable
fun ChapterDetailScreen(
    modifier: Modifier = Modifier,
    selectedChapterArabic: State<List<String>?>,
    selectedChapter: QuranListEntity?,
    function: () -> Unit
) {
    val versePerPage = 10
    val filteredVerses =
        selectedChapterArabic.value?.filter { it != "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ" }
    val pageCount = calculatePageCount(filteredVerses, versePerPage)

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )

    val currentPage = pagerState.currentPage
    val pageCountArabic = toArabicNumber(pageCount)
    val currentPageArabic = toArabicNumber(currentPage + 1)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
        ) {
            DisplayBismillah()

            HorizontalPagerDisplay(
                pagerState = pagerState,
                filteredVerses = filteredVerses,
                versePerPage = versePerPage,
                startIndex = { page -> page * versePerPage },
                endIndex = { page -> minOf((page + 1) * versePerPage, filteredVerses?.size ?: 0) }
            )

            Spacer(modifier = Modifier.weight(1f))

            PageNumberDisplay(currentPageArabic, pageCountArabic)
        }
    }
}

/**
 * Berechnet die Anzahl der Seiten basierend auf der Anzahl der Verse und der Anzahl von Versen pro Seite.
 */
fun calculatePageCount(filteredVerses: List<String>?, versePerPage: Int): Int {
    return (filteredVerses?.size?.plus(versePerPage - 1) ?: 0) / versePerPage
}

/**
 * Zeigt den Bismillah-Text an.
 */
@Composable
fun DisplayBismillah() {
    val bismillah = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ"
    Text(
        text = bismillah,
        style = MaterialTheme.typography.displaySmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}

/**
 * Zeigt die Verse an, die in einem HorizontalPager angezeigt werden.
 */
@Composable
fun HorizontalPagerDisplay(
    pagerState: PagerState,
    filteredVerses: List<String>?,
    versePerPage: Int,
    startIndex: (Int) -> Int,
    endIndex: (Int) -> Int
) {
    HorizontalPager(
        state = pagerState,
    ) { page ->
        val start = startIndex(page)
        val end = endIndex(page)

        FlowRow(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            filteredVerses
                ?.subList(start, end)
                ?.forEachIndexed { index, verse ->
                    DisplayVerse(verse, start + index)
                }
        }
    }
}

/**
 * Zeigt einen einzelnen Vers mit seiner Nummer an.
 */
@Composable
fun DisplayVerse(verse: String, verseNumber: Int) {
    val verseArabic = toArabicNumber(verseNumber + 1)
    val inlineContentId = "index"

    val annotatedString = buildAnnotatedString {
        append(verse)
        appendInlineContent(inlineContentId, "[index]")
    }

    val inlineContent = mapOf(
        inlineContentId to InlineTextContent(
            Placeholder(
                width = 24.sp,
                height = 24.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        )
        {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(30.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50))
                )
                {
                    Text(
                        text = verseArabic,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

        }
    )

    Text(
        text = annotatedString,
        inlineContent = inlineContent,
        modifier = Modifier.clickable {
            println("Verse clicked!")
        },
        style = MaterialTheme.typography.bodyLarge
    )
}

/**
 * Zeigt die aktuelle Seitenzahl und die Gesamtseitenzahl an.
 */
@Composable
fun PageNumberDisplay(currentPageArabic: String, pageCountArabic: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (pageCountArabic.isNotEmpty()) {
            Text(
                text = "$currentPageArabic / $pageCountArabic",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


//
//@Composable
//private fun ExtractWordsWithVerseNumbers(
//    selectedChapter: State<List<String>?>,
//    words: MutableList<String>
//) {
//    val bismillah = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ"
//    val verses = selectedChapter.value.orEmpty()
//    val hasBismillah = verses.firstOrNull() == bismillah
//
//    verses.forEachIndexed { index, verse ->
//        if (hasBismillah) {
//            if (index == 0) {
//                words.addAll(verse.split(" "))
//            } else {
//                val arabicNum = toArabicNumber(index)
//                words.addAll(verse.split(" ") + arabicNum)
//            }
//        } else {
//            val arabicNum = toArabicNumber(index + 1)
//            words.addAll(verse.split(" ") + arabicNum)
//        }
//    }
//}
//
//
//private fun putWordsInLine(
//    words: MutableList<String>,
//    currentLine: String,
//    lines: MutableList<String>
//): String {
//
//    val maxCharsPerLine = 104
//    var currentLine1 = currentLine
//    words.forEach { word ->
//        if (("$currentLine1 $word").trim().length <= maxCharsPerLine) {
//            currentLine1 = "$currentLine1 $word"
//        } else {
//            lines.add(currentLine1.trim())
//            currentLine1 = word
//        }
//    }
//    return currentLine1
//}
