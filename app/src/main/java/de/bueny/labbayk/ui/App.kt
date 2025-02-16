package de.bueny.labbayk.ui

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import de.bueny.labbayk.ui.screens.QuranScreen

@OptIn(UnstableApi::class)
@Composable
fun App() {

    val quranViewModel: QuranViewModel = viewModel()
    val chapter = quranViewModel.chapter.collectAsState()

    Scaffold(
        topBar = {
//            TopBar(
//                navigateUp = { },
//                canNavigateBack = true,
//                onTitleChange = { },
//                viewModel = quranViewModel,
//                chapter = chapter
//
//            )
        }
    ) { innerPadding ->
        QuranScreen(
            Modifier.padding(innerPadding),
            chapter = chapter,
            onVerseClick = { vers -> println("Geklickt: $vers") }
        )
    }

}

