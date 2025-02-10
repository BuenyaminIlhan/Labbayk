package de.bueny.labbayk.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApi
import de.bueny.labbayk.data.repository.QuranRepository
import de.bueny.labbayk.data.repository.QuranRepositoryInterface
import kotlinx.coroutines.launch

class QuranViewModel(application: Application): AndroidViewModel(application) {

    private val quranRepository: QuranRepositoryInterface = QuranRepository(QuranApi)

    init {
        getChapter(
            surahNumber = 112
        )
    }

    private fun getChapter(surahNumber: Int) {
        viewModelScope.launch {
            val chapterResponse = quranRepository.getChapter(surahNumber)
            val chapter: ChapterResponse = chapterResponse
            Log.d("QuranViewModel", "Chapter: $chapter")
        }


    }
}