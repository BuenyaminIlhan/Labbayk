package de.bueny.labbayk.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.bueny.labbayk.data.local.ChapterArabic1
import de.bueny.labbayk.data.local.QuranDatabase
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.data.remote.ChapterAudioResponse
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApiArabic
import de.bueny.labbayk.data.remote.QuranApiGerman
import de.bueny.labbayk.data.local.QuranVerseGerman
import de.bueny.labbayk.data.repository.QuranRepository
import de.bueny.labbayk.data.repository.QuranRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val quranRepository: QuranRepositoryInterface
    private val _arabic1 = MutableStateFlow<List<ChapterArabic1>>(emptyList())
    val arabic1 = _arabic1.asStateFlow()
    private var _quranList = MutableStateFlow<List<QuranListEntity>>(emptyList())
    val quranList = _quranList.asStateFlow()

    private val _filteredQuranList = MutableStateFlow<List<QuranListEntity>>(emptyList())
    var filteredQuranList = _filteredQuranList.asStateFlow()

    private val _germanVerses = MutableStateFlow<QuranVerseGerman?>(null)
    val germanVerses = _germanVerses.asStateFlow()
    private val _favoritesList = MutableStateFlow<List<QuranVerseGerman>>(emptyList())
    val favoritesList = _favoritesList.asStateFlow()

    init {
        val quranDB = QuranDatabase.getDB(application)
        val quranListDao = quranDB.quranListDao
        val chapterArabicDao = quranDB.chapterArabicDao
        val chapterGermanDao = quranDB.chapterGermanDao

        quranRepository = QuranRepository(
            QuranApiArabic, QuranApiGerman, quranListDao, chapterGermanDao, chapterArabicDao
        )
        insertAllChaptersToDB()
        insertAllArabicChapterToDB()
        getFavoritesList()
    }

    private fun getFavoritesList() {
        viewModelScope.launch {
            try {
                quranRepository.getFavoriteVerses().collect { favorites ->
                    _favoritesList.value = favorites
                    Log.d("QuranViewModel getFavoritesList", "Favorites: ${favorites.toString()}")
                }
            } catch (e: Exception) {
                Log.d("QuranViewModel getFavoritesList", "Error: ${e.message}")
            }
        }
    }

    fun filterList(surahName: String) {
        _filteredQuranList.value = _quranList.value.filter {
            it.surahName.contains(surahName, ignoreCase = true)
        }
    }

    fun resetFilterQuranList() {
        _filteredQuranList.value = _quranList.value
    }

    private fun getGermanVerseFromDB(chapterId: Int) {
        viewModelScope.launch {
            try {
                val germanVerses = quranRepository.getGermanVerse(chapterId)
                _germanVerses.value = germanVerses
            } catch (e: Exception) {
                Log.d("QuranViewModel getGermanVerseFromDB", "Error: ${e.message}")
            }
        }
    }

    fun setSelectedGermanVerse(chapterId: Int) {
        getGermanVerseFromDB(chapterId)
    }

    private fun getQuranList() {
        viewModelScope.launch {
            try {
                val quranList = quranRepository.getQuranListFromLocal()
                val list: List<QuranListEntity> = quranList
                _quranList.value = list
                _filteredQuranList.value = list
            } catch (e: Exception) {
                Log.d("QuranViewModel getQuranList", "Error: ${e.message}")
            }
        }
    }

    private fun insertAllChaptersToDB() = runBlocking {

        viewModelScope.launch {

            val localData = quranRepository.getQuranListFromLocal()
            if (localData.isEmpty()) {
                try {
                    runBlocking {
                        insertGermanVersesToRoom()
                    }
                    val quranList = quranRepository.getQuranList()

                    runBlocking {
                        val result = launch { quranRepository.insertQuranListToLocal(quranList) }
                        result.join()
                    }
                } catch (e: Exception) {
                    Log.e("QuranViewModel insertAllChaptersToDB", "Fehler: ${e.message}")
                }
            }
            getQuranList()
        }
    }

    private fun insertGermanVersesToRoom() {
        viewModelScope.launch {
            try {
                val chapterResponse = quranRepository.getChapterGerman()
                val verseList = chapterResponse.quran.toList()

                for (verse in verseList) {
                    val quranVerse = QuranVerseGerman(
                        chapter = verse.chapter,
                        verse = verse.verse,
                        text = verse.text,
                    )
                    quranRepository.insertGermanVersesToLocal(quranVerse)
                }
            } catch (e: Exception) {
                Log.d("QuranViewModel insertGermanVersesToRoom", "Error: ${e.message}")
            }
        }
    }

    private fun insertAllArabicChapterToDB() {
        viewModelScope.launch {
            try {
                val chapterCount = quranRepository.getChapterCount()
                if (chapterCount > 0) {
                    return@launch
                }

                for (surahNumber in 1..114) {
                    val chapterResponse = quranRepository.getChapter(surahNumber)
                    val chapter: ChapterResponse = chapterResponse
                    val chapterId = chapter.surahNo
                    val audioResponse = chapterResponse.audio
                    val audios: Map<String, ChapterAudioResponse> = audioResponse

                    quranRepository.insertChapterToLocal(chapter)
                    quranRepository.insertChapterAudiosToLocal(chapterId, audios)

                    insetArabicVerseToDB(chapter)
                }
            } catch (e: Exception) {
                Log.e(
                    "QuranViewModel insertAllArabicChapterToDB",
                    "Fehler beim Laden der Kapitel: ${e.message}"
                )
            }
        }
    }

    private suspend fun insetArabicVerseToDB(chapter: ChapterResponse) {
        for (verse in chapter.arabic1) {
            quranRepository.insertChapterArabic1ToLocal(chapter.surahNo, verse)
        }
    }

    fun getArabicChapterFormDB(chapterId: Int) {
        viewModelScope.launch {
            try {
                val result = quranRepository.getChapterArabic1(chapterId)
                _arabic1.value = result
            } catch (e: Exception) {
                Log.d("QuranViewModel getArabicChapterFormDB", "Error: ${e.message}")
            }
        }
    }


    fun updateFavoriteStatus(id: Int, isFav: Boolean) {
        viewModelScope.launch {
            try {
                quranRepository.updateFavoriteStatus(id, isFav)
                _germanVerses.value?.let {
                    if (it.id == id) {
                        _germanVerses.value = it.copy(isFav = isFav)
                    }
                }
            } catch (e: Exception) {
                Log.d("QuranViewModel updateFavoriteStatus", "Error: ${e.message}")
            }
        }
    }

}