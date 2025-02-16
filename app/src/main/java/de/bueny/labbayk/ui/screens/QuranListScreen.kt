package de.bueny.labbayk.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.util.customTextStyle
import de.bueny.labbayk.util.toArabicNumber

@Composable
fun QuranListScreen(
    modifier: Modifier,
    quranList: State<List<QuranListEntity>?>
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(quranList.value ?: emptyList()) { index, chapter ->
            QuranListItem(
                chapter,
                index = index
            )
        }
    }
}


@Composable
fun QuranListItem(chapter: QuranListEntity, index: Int) {
    val backgroundColor = if (index % 2 == 0) Color(0xFF90A4CE) else Color(0xFFD2AC61)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${index + 1}" + " " + chapter.surahName,
                    style = customTextStyle()
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = chapter.surahNameArabic + " " + toArabicNumber(index + 1),
                    style = customTextStyle()
                )
            }
        }
    }
}





