package de.bueny.labbayk.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.ui.QuranViewModel
import de.bueny.labbayk.util.customTextStyle
import de.bueny.labbayk.util.toArabicNumber

@Composable
fun QuranListScreen(
    quranList: State<List<QuranListEntity>?>,
    modifier: Modifier,
    quranViewModel: QuranViewModel,
    onItemClick: () -> Unit,
    ) {
        Column{
        SearchField { }

        LazyColumn {
            itemsIndexed(quranList.value ?: emptyList()) { index, chapter ->
                QuranListItem(
                    chapter, index, quranViewModel,
                    onItemClick = { onItemClick() }
                ) 
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun QuranListItem(
    chapter: QuranListEntity,
    index: Int,
    quranViewModel: QuranViewModel,
    onItemClick: () -> Unit
) {
    val backgroundColor = if (index % 2 == 0) Color(0xFF90A4CE).copy(alpha = 0.6f)
    else Color(0xFFD2AC61).copy(alpha = 0.6f)

    val surahNo = index + 1

    Column {
        Box(
            modifier = Modifier
                .clickable {
                    quranViewModel.getChapterArabic1(surahNo)
                    onItemClick()
                }
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$surahNo ${chapter.surahName}",
                    style = customTextStyle()
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = chapter.surahNameArabic + " " + toArabicNumber(index + 1),
                    style = customTextStyle()
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.5f)
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
fun SearchField(
    onTitleChange: (String) -> Unit,
) {
    var localTitle by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        value = localTitle,
        onValueChange = { newText ->
            localTitle = newText
            onTitleChange(newText)
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (localTitle.isNotEmpty()) {
                IconButton(onClick = { localTitle = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = Icons.Default.Clear.name)
                }
            }
        },
        label = { Text("Kapitelnummer") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                Log.d("TopBar", "onDone: $localTitle")
            }
        )
    )
}