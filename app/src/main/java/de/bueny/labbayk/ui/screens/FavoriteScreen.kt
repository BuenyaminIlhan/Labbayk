package de.bueny.labbayk.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.bueny.labbayk.ui.QuranViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier,
    quranViewModel: QuranViewModel,
    onFavoriteClick: (Int) -> Unit // Callback, der beim Klick auf ein Element aufgerufen wird
) {

    val favorites = quranViewModel.favoritesList.collectAsState()

    val backgroundColor1 = Color(0xFFD8C8A9) // Dunkelgrün für die erste Karte
    val backgroundColor2 = Color(0xFF556B2F) // Moosgrün für die zweite Karte
    val textColor2 = Color(0xFFF2DFBF) // Weiß für den Text, damit er gut lesbar ist
    val textColor1 = Color.DarkGray // Weiß für den Text, damit er gut lesbar ist


    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 12.dp)
        ) {
            items(favorites.value.size) { i ->
                val backgroundColor = if (i % 2 == 0) backgroundColor1 else backgroundColor2
                val textColor = if (i % 2 == 0) textColor1 else textColor2

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFavoriteClick(i) }
                        .padding(2.dp),
                    colors = CardDefaults.cardColors(backgroundColor)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "Kapitel: ${favorites.value[i].chapter}",
                                    style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Vers: ${favorites.value[i].verse}",
                                    style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Text(
                            text = favorites.value[i].text,
                            style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}







