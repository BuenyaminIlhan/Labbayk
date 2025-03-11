package de.bueny.labbayk.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.bueny.labbayk.data.local.QuranDatabase
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.data.remote.ChapterAudioResponse
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
    private val _arabic1 = MutableStateFlow<List<String>?>(null)
    val arabic1 = _arabic1.asStateFlow()
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
        loadAllChaptersToRoom()
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
                    }
                } catch (e: Exception) {
                    Log.e("QuranViewModel", "Fehler: ${e.message}")
                }
            }
            getQuranList()
        }
    }

    private fun loadAllChaptersToRoom() {
        viewModelScope.launch {
            try {
                val chapterCount = quranRepository.getChapterCount()
                if (chapterCount > 0) {
                    return@launch
                }

                for (surahNumber in 1..114) {
                    Log.d("QuranViewModel", "Lade Kapitel $surahNumber...")

                    val chapterResponse = quranRepository.getChapter(surahNumber)
                    val chapter: ChapterResponse = chapterResponse
                    val chapterId = chapter.surahNo
                    val audioResponse = chapterResponse.audio
                    val audios: Map<String, ChapterAudioResponse> = audioResponse

                    quranRepository.insertChapterToLocal(chapter)
                    quranRepository.insertChapterAudiosToLocal(chapterId, audios)

                    insertVerseToLocal(chapter)
                }
            } catch (e: Exception) {
                Log.e("QuranViewModel", "Fehler beim Laden der Kapitel: ${e.message}")
            }
        }
    }

    private suspend fun insertVerseToLocal(chapter: ChapterResponse) {
        for (verse in chapter.arabic1) {
            quranRepository.insertChapterArabic1ToLocal(chapter.surahNo, verse)
        }
    }

    fun getChapterArabic1(chapterId: Int) {
        viewModelScope.launch {
            val result = quranRepository.getChapterArabic1(chapterId)
            Log.d("QuranViewModel", "getChapterArabic1: $result")
           _arabic1.value = result
        }
    }
}