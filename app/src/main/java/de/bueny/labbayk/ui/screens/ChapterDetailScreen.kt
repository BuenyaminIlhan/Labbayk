package de.bueny.labbayk.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.util.toArabicNumber

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

            HorizontalPagerDisplay(
                pagerState = pagerState,
                filteredVerses = filteredVerses,
                versePerPage = versePerPage,
                startIndex = { page -> page * versePerPage },
                endIndex = { page -> minOf((page + 1) * versePerPage, filteredVerses?.size ?: 0) },
                currentPage = currentPage,
                onVerseClick = {}
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
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}

@Composable
fun HorizontalPagerDisplay(
    pagerState: PagerState,
    filteredVerses: List<String>?,
    versePerPage: Int,
    startIndex: (Int) -> Int,
    endIndex: (Int) -> Int,
    onVerseClick: (String) -> Unit,
    currentPage: Int
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top,
    ) { page ->
        val start = startIndex(page)
        val end = endIndex(page)

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Nur die Verse für die aktuelle Seite anzeigen
            filteredVerses?.subList(start, end.coerceAtMost(filteredVerses.size))
                ?.forEachIndexed { index, verse ->
                    DisplayVerse(verse, start + index, onVerseClick) // Korrigierter Index
                }
        }
    }
}

@Composable
private fun DisplayVerse(
    verse: String,
    index: Int,
    onVerseClick: (String) -> Unit
) {
    val indexArabic = toArabicNumber(index + 1)

    val annotatedString = buildAnnotatedString {
        append(verse)
        append(" ")
        appendInlineContent("indexTag", "[index]")
    }

    val inlineContent = mapOf(
        "indexTag" to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(35))
                    .background(Color(0xFF4CAF50))
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = indexArabic,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                    )
            }
        }
    )

    Text(
        modifier = Modifier
            .clickable {},
        text = annotatedString,
        inlineContent = inlineContent,
        fontSize = 16.sp,
        style = MaterialTheme.typography.bodyLarge
    )
}


@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDisplay(
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isSheetOpen) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { onDismissRequest() }
            ) {
                Text(
                    "Swipe up to open sheet. Swipe down to dismiss.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
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