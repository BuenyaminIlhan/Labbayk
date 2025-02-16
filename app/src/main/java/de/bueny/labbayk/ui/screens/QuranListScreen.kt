package de.bueny.labbayk.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.bueny.labbayk.data.remote.ChapterListResponse
import de.bueny.labbayk.util.customTextStyle

@Composable
fun QuranListScreen(
    modifier: Modifier,
    quranList: State<List<ChapterListResponse>?>
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(quranList.value ?: emptyList()) { chapter ->
            QuranListItem(chapter)
        }
    }
}

@Composable
fun QuranListItem(chapter: ChapterListResponse) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(text = chapter.surahName)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = chapter.surahNameArabic,
                style = customTextStyle()
            )
        }
    }
}


