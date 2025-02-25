package de.bueny.labbayk.ui.components

import android.annotation.SuppressLint
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.AlbumsTab.value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.ui.QuranViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    onTitleChange: (String) -> Unit,
    viewModel: QuranViewModel,
    chapter: State<ChapterResponse?>
) {
    var localTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TopAppBar(
            title = { TopAppBarTitle(chapter) },
//            navigationIcon = {
//                if (canNavigateBack) {
//                    IconButton(onClick = navigateUp) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                label = { Text("Kapitelnummer") }
            )
        }
    }
}


@Composable
fun TopAppBarTitle(chapter: State<ChapterResponse?>) {
    Row {
        chapter.value?.let { Text(it.surahName) }
        Spacer(modifier = Modifier.weight(1f))
        chapter.value?.let { Text(it.surahNameArabic) }
    }
}


//@Composable
//fun MainScreen() {
//    // Verwende MutableState, u den Titel zu verwalten
//    var title by remember { mutableStateOf("Initial Title") }
//
//    TopBar(
//        navigateUp = { /* Handle Back Navigation */ },
//        canNavigateBack = true,
//        title = title,
//        onTitleChange = { newTitle -> title = newTitle },
//        modifier = TODO() // Update des Titels
//    )
//}

