package de.bueny.labbayk.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.bueny.labbayk.data.local.QuranDatabase
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApi
import de.bueny.labbayk.data.repository.QuranRepository
import de.bueny.labbayk.data.repository.QuranRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val quranRepository: QuranRepositoryInterface

    private val _chapter = MutableStateFlow<ChapterResponse?>(null)
    val chapter = _chapter

    private val _quranList = MutableStateFlow<List<QuranListEntity>?>(null)
    val quranList = _quranList.asStateFlow()

    init {

        val quranDB = QuranDatabase.getDB(application)
        val quranListDao = quranDB.quranListDao
        val chapterDao = quranDB.chapterDao

        quranRepository = QuranRepository(
            QuranApi, quranListDao, chapterDao
        )
        loadQuranListToRoom()
        //getChapter()

    }

    fun getChapter(surahNumber: Int = 56) {
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

    private fun getQuranList() {
        viewModelScope.launch {
            try {
                val quranList = quranRepository.getQuranListFromLocal()
                val list: List<QuranListEntity> = quranList
                _quranList.value = list
                Log.d("QuranViewModel", "Quran List: $quranList")
            } catch (e: Exception) {
                Log.d("QuranViewModel", "Error: ${e.message}")
            }
        }
    }

    private fun loadQuranListToRoom() = runBlocking {

        viewModelScope.launch {

            val localData = quranRepository.getQuranListFromLocal()
            if (localData.isEmpty()) {
                try {
                    val quranList = quranRepository.getQuranList()
                    runBlocking {
                        val result = launch { quranRepository.insertQuranListToLocal(quranList) }
                        result.join()
                        getQuranList()
                    }
                } catch (e: Exception) {
                    Log.e("QuranViewModel", "Fehler: ${e.message}")
                }
            }
            getQuranList()
        }
    }

    private fun loadChapterToRoom(surahNumber: Int) = runBlocking {
        viewModelScope.launch {
            try {
                val chapterResponse = quranRepository.getChapter(surahNumber)
                val chapter: ChapterResponse = chapterResponse
                _chapter.value = chapter
                Log.d("QuranViewModel", "Chapter: $chapter")
                quranRepository.insertChapterToLocal(chapter)
            } catch (e: Exception) {
                Log.d("QuranViewModel", "Error: ${e.message}")
            }
        }

    }

}