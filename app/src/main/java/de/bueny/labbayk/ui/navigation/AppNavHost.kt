package de.bueny.labbayk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.bueny.labbayk.ui.QuranViewModel
import de.bueny.labbayk.ui.screens.FavoritesScreen
import de.bueny.labbayk.ui.screens.HomeScreen
import de.bueny.labbayk.ui.screens.QuranListScreen

@Composable
fun AppNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
    selectedNavItem: NavigationItem,
    quranViewModel: QuranViewModel
) {

    val quranList = quranViewModel.quranList.collectAsState()

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
                quranList = quranList,
                modifier = modifier
            )
        }
    }
}