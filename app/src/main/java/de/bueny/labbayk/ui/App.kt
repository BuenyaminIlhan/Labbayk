package de.bueny.labbayk.ui

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import de.bueny.labbayk.ui.components.BottomNavigationBar
import de.bueny.labbayk.ui.components.TopBar
import de.bueny.labbayk.ui.navigation.AppNavHost
import de.bueny.labbayk.ui.navigation.NavigationItem
import de.bueny.labbayk.ui.screens.SearchField

@OptIn(UnstableApi::class)
@Composable
fun App() {
    val navHostController = rememberNavController()
    val quranViewModel: QuranViewModel = viewModel()
    val chapter = quranViewModel.chapter.collectAsState()
    var selectedNavItem by rememberSaveable { mutableStateOf(NavigationItem.QuranList) }
    val primaryBackgroundColor = Color(0xFFF4E1C1)
    val secondaryBackgroundColor = Color(0xFF2E3B3E)

    Scaffold(
        containerColor = primaryBackgroundColor,
        topBar = {
            SearchField { }
            if (selectedNavItem == NavigationItem.QuranList) {
                TopBar(
                    backgroundColor = primaryBackgroundColor,
                    navigateUp = { },
                    canNavigateBack = true,
                    onTitleChange = { },
                    viewModel = quranViewModel,
                    chapter = chapter
                )
            }
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
        Column(Modifier.padding(innerPadding)) {
            AppNavHost(
                modifier = Modifier
                    //.padding(innerPadding)
                    .background(primaryBackgroundColor),
                navHostController = navHostController,
                selectedNavItem = selectedNavItem,
                quranViewModel = quranViewModel
            )
        }
    }
}




