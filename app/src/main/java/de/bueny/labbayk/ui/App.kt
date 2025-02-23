package de.bueny.labbayk.ui

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import de.bueny.labbayk.ui.components.BottomNavigationBar
import de.bueny.labbayk.ui.components.TopBar
import de.bueny.labbayk.ui.navigation.NavigationItem
import de.bueny.labbayk.ui.screens.QuranListScreen

@OptIn(UnstableApi::class)
@Composable
fun App() {
    val navHostController = rememberNavController()
    val quranViewModel: QuranViewModel = viewModel()
    val chapter = quranViewModel.chapter.collectAsState()
    val quranList = quranViewModel.quranList.collectAsState()
    var selectedNavItem by rememberSaveable { mutableStateOf(NavigationItem.Home) }


    Scaffold(
        topBar = {
            TopBar(
                navigateUp = { },
                canNavigateBack = true,
                onTitleChange = { },
                viewModel = quranViewModel,
                chapter = chapter

            )
        },
        bottomBar = {
            BottomNavigationBar(
                navHostController,
                selectedNavItem,
                onItemSelected = {
                    selectedNavItem = it
                },
            )
        }
    ) { innerPadding ->
//        QuranScreen(
//            Modifier.padding(innerPadding),
//            chapter = chapter,
//            onVerseClick = { vers -> println("Geklickt: $vers") }
//        )

        QuranListScreen(
            Modifier.padding(innerPadding),
            quranList = quranList
        )
    }
}



