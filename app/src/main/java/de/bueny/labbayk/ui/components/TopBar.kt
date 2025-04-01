package de.bueny.labbayk.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
    backgroundColor: Color = Color.Gray,
    onTitleChange: () -> Unit
) {

    Column(modifier = Modifier.background(backgroundColor)) {
        TopAppBar(
            title = { Text("Quran") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor
            )
        )
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

