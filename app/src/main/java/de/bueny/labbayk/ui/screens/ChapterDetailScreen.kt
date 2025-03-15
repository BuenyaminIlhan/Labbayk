package de.bueny.labbayk.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import de.bueny.labbayk.data.local.ChapterArabic1
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.data.local.QuranVerseGerman
import de.bueny.labbayk.ui.QuranViewModel
import de.bueny.labbayk.util.toArabicNumber

@OptIn(UnstableApi::class)
@Composable
fun ChapterDetailScreen(
    modifier: Modifier = Modifier,
    selectedChapterArabic: State<List<ChapterArabic1>?>,
    selectedChapter: QuranListEntity?,
    quranViewModel: QuranViewModel,
    function: () -> Unit
) {
    val selectedVerseGerman = quranViewModel.germanVerses.collectAsState()
    val versePerPage = 6
    val filteredVerses = selectedChapterArabic.value?.let { arabicList ->
        arabicList.filter { verse ->
            verse.arabic1 != "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ"
        }
    }

    val pageCount = calculatePageCount(filteredVerses, versePerPage)
    var isSheetOpen by remember { mutableStateOf(false) }

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
            println("selected: ${selectedChapterArabic.value.toString()}")
            HorizontalPagerDisplay(
                pagerState = pagerState,
                filteredVerses = filteredVerses,
                versePerPage = versePerPage,
                startIndex = { page -> page * versePerPage },
                endIndex = { page -> minOf((page + 1) * versePerPage, filteredVerses?.size ?: 0) },
                currentPage = currentPage,
                onVerseClick = { verseNumber ->
                    isSheetOpen = true
                    quranViewModel.setSelectedGermanVerse(verseNumber)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomSheetDisplay(
                isSheetOpen, selectedVerseGerman,
                onDismissRequest = { isSheetOpen = false },
                onClickFavorite = {
                    quranViewModel.updateFavoriteStatus(
                        it.id, !it.isFav
                    )
                },

                )
            PageNumberDisplay(currentPageArabic, pageCountArabic)
        }
    }
}

/**
 * Berechnet die Anzahl der Seiten basierend auf der Anzahl der Verse und der Anzahl von Versen pro Seite.
 */
fun calculatePageCount(filteredVerses: List<ChapterArabic1>?, versePerPage: Int): Int {
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
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun HorizontalPagerDisplay(
    pagerState: PagerState,
    filteredVerses: List<ChapterArabic1>?,
    startIndex: (Int) -> Int,
    endIndex: (Int) -> Int,
    onVerseClick: (Int) -> Unit,
    currentPage: Int,
    versePerPage: Int
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
            filteredVerses
                ?.subList(start, end)
                ?.forEachIndexed { index, verse ->
                    if (currentPage == 0 && start + index == 0) {
                        DisplayBismillah()
                    }
                    DisplayVerse(verse, start + index, onVerseClick)
                }
        }
    }
}

@Composable
private fun DisplayVerse(
    verse: ChapterArabic1?,
    index: Int,
    onVerseClick: (Int) -> Unit
) {
    val indexArabic = toArabicNumber(index + 1)
    val annotatedString = buildAnnotatedString {
        if (verse != null) {
            append(verse.arabic1)
        }
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
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    )

    Text(
        modifier = Modifier
            .clickable {
                onVerseClick(verse?.id ?: return@clickable)
            },
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
    selectedVerseGerman: State<QuranVerseGerman?>,
    onDismissRequest: () -> Unit,
    onClickFavorite: (QuranVerseGerman) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    if (isSheetOpen) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { onDismissRequest() },
            contentColor = Color(0xFF3E3E3E),
            containerColor = Color(0xFFF5F5F5)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(
                        imageVector = if (selectedVerseGerman.value?.isFav == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorit",
                        tint = Color(0xFFE57373),
                        modifier = Modifier
                            .height(24.dp)
                            .clickable {
                                selectedVerseGerman.value?.let(onClickFavorite)
                            }
                    )

                    Text(
                        text = "2 / 52",
                        color = Color(0xFF3E3E3E),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                selectedVerseGerman.value?.text?.let {
                    Text(
                        it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        color = Color(0xFF3E3E3E),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Frank Bubenheim",
                    color = Color(0xFF3E3E3E),
                    style = MaterialTheme.typography.bodyMedium
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