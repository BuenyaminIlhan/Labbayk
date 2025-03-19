package de.bueny.labbayk.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
    onTitleChange: (QuranListEntity) -> Unit
    ) {
        Column{
        SearchField { }

        LazyColumn {
            itemsIndexed(quranList.value ?: emptyList()) { index, chapter ->
                QuranListItem(
                    chapter, index, quranViewModel,
                    onItemClick = { onItemClick() },
                    onTitleChange = onTitleChange
                )
            }
        }
    }
}

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    onTitleChange: (String) -> Unit,
) {
    var localTitle by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .shadow(2.dp, shape = RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        value = localTitle,
        onValueChange = { newText ->
            localTitle = newText
            onTitleChange(newText)
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            if (localTitle.isNotEmpty()) {
                IconButton(onClick = { localTitle = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                }
            }
        },
        label = { Text("Suchen", color = Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                Log.d("TopBar", "onDone: $localTitle")
            }
        ),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent, // Keine Unterstreichung
            unfocusedIndicatorColor = Color.Transparent // Keine Unterstreichung
        )
    )
}

@OptIn(UnstableApi::class)
@Composable
fun QuranListItem(
    chapter: QuranListEntity,
    index: Int,
    quranViewModel: QuranViewModel,
    onItemClick: () -> Unit,
    onTitleChange: (QuranListEntity) -> Unit
) {
    val backgroundColor = if (index % 2 == 0) Color(0xFF90A4CE).copy(alpha = 0.6f)
    else Color(0xFFD2AC61).copy(alpha = 0.6f)

    val surahNo = index + 1

    Column {
        Box(
            modifier = Modifier
                .clickable {
                    quranViewModel.getArabicChapterFormDB(surahNo)
                    onItemClick()
                    onTitleChange(chapter)
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
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollAwareSearchField(
    onTitleChange: (String) -> Unit
) {
    var localTitle by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var isSearchVisible by remember { mutableStateOf(false) }
    var lastScrollPosition by remember { mutableStateOf(0) }

    LaunchedEffect(scrollState.value) {
        isSearchVisible = scrollState.value < lastScrollPosition
        lastScrollPosition = scrollState.value
    }

    Column {
        if (isSearchVisible) {
            TextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 6.dp)
                    .fillMaxWidth()
                    .shadow(2.dp, shape = RoundedCornerShape(12.dp))
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                value = localTitle,
                onValueChange = { newText ->
                    localTitle = newText
                    onTitleChange(newText)
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                },
                trailingIcon = {
                    if (localTitle.isNotEmpty()) {
                        IconButton(onClick = { localTitle = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                        }
                    }
                },
                label = { Text("Suchen", color = Color.Gray) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        Log.d("TopBar", "onDone: $localTitle")
                    }
                ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Dummy-Scrollable-Content zum Testen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            repeat(50) {
                Text("Item $it", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

