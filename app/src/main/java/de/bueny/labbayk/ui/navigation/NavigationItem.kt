package de.bueny.labbayk.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import de.bueny.labbayk.R


enum class NavigationItem(
    val label: Int,
    val icon: ImageVector,
    val route: Any,
) {
    Home(
        label = R.string.home,
        icon = Icons.Filled.Home,
        route = HomeRoute,
    ),

    QuranList(
        label = R.string.quran_list,
        icon = Icons.Filled.Book,
        route = QuranListRoute,
    ),

    Favorites(
        label = R.string.favorites,
        icon = Icons.Filled.Favorite,
        route = FavoritesRoute,
    ),

}