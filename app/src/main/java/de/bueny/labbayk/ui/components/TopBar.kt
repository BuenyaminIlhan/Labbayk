package de.bueny.labbayk.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.ui.QuranViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    viewModel: QuranViewModel,
    chapter: State<ChapterResponse?>,
    backgroundColor: Color = Color.Gray,
    onTitleChange: () -> Unit
) {

    Column(modifier = Modifier.background(backgroundColor)) {
//        TopAppBar(
//            title = { Text("Quran") },
//            colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = backgroundColor
//            )
//        )
        Row(
            modifier = Modifier
                .background(backgroundColor)
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

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

