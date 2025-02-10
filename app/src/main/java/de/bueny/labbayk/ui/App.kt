package de.bueny.labbayk.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun App(modifier: Modifier = Modifier) {

    val quranViewModel = QuranViewModel(application = Application())

}