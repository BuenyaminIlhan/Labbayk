package de.bueny.labbayk.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApi
import de.bueny.labbayk.data.repository.QuranRepository
import de.bueny.labbayk.data.repository.QuranRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val quranRepository: QuranRepositoryInterface = QuranRepository(QuranApi)
    private val _chapter = MutableStateFlow<ChapterResponse?>(null)
    val chapter = _chapter


    init {
        getChapter()
    }

    fun getChapter(surahNumber: Int = 2) {
        viewModelScope.launch {

            try {
                val chapterResponse = quranRepository.getChapter(surahNumber)
                val chapter: ChapterResponse = chapterResponse
                _chapter.value = chapter
                Log.d("QuranViewModel", "Chapter: $chapter")
            } catch (e: Exception) {
                Log.d("QuranViewModel", "Error: ${e.message}")
            }
        }
    }
}