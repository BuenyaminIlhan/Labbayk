package de.bueny.labbayk.ui.navigation

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.ui.QuranViewModel
import de.bueny.labbayk.ui.screens.ChapterDetailScreen
import de.bueny.labbayk.ui.screens.FavoritesScreen
import de.bueny.labbayk.ui.screens.HomeScreen
import de.bueny.labbayk.ui.screens.QuranListScreen

@SuppressLint("UnrememberedMutableState")
@OptIn(UnstableApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
    selectedNavItem: NavigationItem,
    quranViewModel: QuranViewModel
) {

    val quranList = quranViewModel.quranList.collectAsState()
    val selectedChapter = quranViewModel.arabic1.collectAsState()
    var selectedChapterTitle by remember { mutableStateOf<QuranListEntity?>(null) }

    NavHost(
        navController = navHostController,
        startDestination = selectedNavItem.route,
    ) {

        composable<HomeRoute> {
            HomeScreen()
        }

        composable<FavoritesRoute> {
            FavoritesScreen(
                modifier = modifier
            )
        }

        composable<QuranListRoute> {
            QuranListScreen(
                quranList, modifier, quranViewModel,
                onTitleChange = { chapterTitle ->
                    selectedChapterTitle = chapterTitle
                },
                onItemClick = { navHostController.navigate("chapterDetail") }
            )
        }

        composable("chapterDetail") {
            ChapterDetailScreen(modifier, selectedChapter, selectedChapterTitle, quranViewModel) { }
        }
    }
}