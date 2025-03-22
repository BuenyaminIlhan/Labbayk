package de.bueny.labbayk.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.bueny.labbayk.data.local.ChapterArabic1
import de.bueny.labbayk.data.local.QuranVerseGerman

@Composable
fun FavoriteDetailScreen(
    modifier: Modifier = Modifier,
    selectedFavorite: State<QuranVerseGerman?>,
    selectedChapter: State<List<ChapterArabic1>>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedFavorite.value?.chapter?.toString() ?: "-",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = " / ",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = selectedFavorite.value?.verse?.toString() ?: "-",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            text = selectedFavorite.value?.text ?: "Kein Text verfügbar",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = "Autor: Frank Bubenheim",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}
