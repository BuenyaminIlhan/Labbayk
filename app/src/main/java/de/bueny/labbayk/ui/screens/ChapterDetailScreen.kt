package de.bueny.labbayk.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
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
    selectedChapter: State<List<String>?>,
    selectedChapterTitle: QuranListEntity?,
    function: () -> Unit
) {
    val versePerPage = 11
    val pageCount = (selectedChapter.value?.size?.plus(versePerPage - 1) ?: 0) / versePerPage
    val bismillah = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ"
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )
    val pageCountArabic = toArabicNumber(pageCount)
    val currentPageArabic = toArabicNumber(pagerState.currentPage + 1)
    if (selectedChapterTitle != null) {
        Log.d("ChapterDetailScreen", selectedChapterTitle.surahNameArabic)
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val startIndex = page * versePerPage
                val endIndex = minOf(startIndex + versePerPage, selectedChapter.value?.size ?: 0)

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedChapter.value
                        ?.subList(startIndex, endIndex)
                        ?.flatMap { it.split(" ") }
                        ?.forEach { word ->
                            Text(
                                text = word,
                                modifier = Modifier.clickable { },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (pageCount > 1) {
                    Text(
                        text = "$currentPageArabic / $pageCountArabic",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
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
