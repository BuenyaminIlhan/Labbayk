package de.bueny.labbayk.ui.navigation

import android.os.Bundle
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object QuranListRoute

@Serializable
object FavoritesRoute

@Serializable
object ChapterDetailRoute {
    const val route = "chapterDetail" }

@Serializable
object FavoriteDetailScreen {
    const val route = "favoriteDetailScreen"
}
