package de.bueny.labbayk.ui

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.bueny.labbayk.ui.components.BottomNavigationBar
import de.bueny.labbayk.ui.navigation.AppNavHost
import de.bueny.labbayk.ui.navigation.ChapterDetailRoute
import de.bueny.labbayk.ui.navigation.FavoriteDetailScreen
import de.bueny.labbayk.ui.navigation.NavigationItem

@SuppressLint("RestrictedApi")
@OptIn(UnstableApi::class)
@Composable
fun App() {
    val navHostController = rememberNavController()
    val quranViewModel: QuranViewModel = viewModel()

    var selectedNavItem by rememberSaveable { mutableStateOf(NavigationItem.QuranList) }
    val primaryBackgroundColor = Color(0xFFF4E1C1)
    val secondaryBackgroundColor = Color(0xFF2E3B3E)

    val currentBackStack by navHostController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val showBottomBarOnDetail = currentDestination?.hierarchy?.any { it.route == ChapterDetailRoute.route } == true
    val showBottomBarOnFavoriteDetail = currentDestination?.hierarchy?.any { it.route == FavoriteDetailScreen.route } == true

    Scaffold(
        containerColor = primaryBackgroundColor,
        topBar = {
                CustomTopBar(
                    primaryBackgroundColor, quranViewModel,navHostController
                )
        },
        bottomBar = {
            if (!showBottomBarOnDetail && !showBottomBarOnFavoriteDetail) {
                BottomNavigationBar(
                    navHostController,
                    selectedNavItem,
                    onItemSelected = {
                        selectedNavItem = it
                    },
                )
            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            AppNavHost(
                modifier = Modifier
                    .background(primaryBackgroundColor),
                navHostController = navHostController,
                selectedNavItem = selectedNavItem,
                quranViewModel = quranViewModel
            )
        }
    }
}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTopBar(
    primaryBackgroundColor: Color,
    quranViewModel: QuranViewModel,
    navController: NavController
) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val showBackButtonOnDetail = currentDestination?.hierarchy?.any { it.route == ChapterDetailRoute.route } == true
    val showBackButtonOnFavorite = currentDestination?.hierarchy?.any { it.route == FavoriteDetailScreen.route } == true


    TopAppBar(
        title = {
            Text(
                when {
                    showBackButtonOnDetail -> "Detail"
                    showBackButtonOnFavorite -> "Favoriten"
                    else -> ""
                }
            )
        }
,
        navigationIcon = {
            if (showBackButtonOnDetail || showBackButtonOnFavorite) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = Icons.AutoMirrored.Filled.ArrowBack.name
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = primaryBackgroundColor
        )
    )
}
