package de.bueny.labbayk.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.bueny.labbayk.data.remote.ChapterListResponse
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApi
import de.bueny.labbayk.data.repository.QuranRepository
import de.bueny.labbayk.data.repository.QuranRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val quranRepository: QuranRepositoryInterface = QuranRepository(QuranApi)

    private val _chapter = MutableStateFlow<ChapterResponse?>(null)
    val chapter = _chapter

    private val _quranList = MutableStateFlow<List<ChapterListResponse>?>(null)
    val quranList = _quranList.asStateFlow()

    init {
        getQuranList()
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

    fun getQuranList() {
        viewModelScope.launch {
            try {
                val quranList = quranRepository.getQuranList()
                val list: List<ChapterListResponse> = quranList
                _quranList.value = list
                Log.d("QuranViewModel", "Quran List: $quranList")
            } catch (e: Exception) {
                Log.d("QuranViewModel", "Error: ${e.message}")
            }
        }
    }
}