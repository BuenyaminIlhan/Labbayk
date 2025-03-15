package de.bueny.labbayk.data.repository

import androidx.compose.runtime.MutableState
import androidx.room.Dao
import de.bueny.labbayk.data.local.ChapterArabic1
import de.bueny.labbayk.data.local.ChapterAudio
import de.bueny.labbayk.data.local.ChapterArabicDao
import de.bueny.labbayk.data.local.ChapterEntity
import de.bueny.labbayk.data.local.ChapterGermanDao
import de.bueny.labbayk.data.local.QuranListDao
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.data.remote.ChapterAudioResponse
import de.bueny.labbayk.data.remote.ChapterListResponse
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.ChapterResponseGerman
import de.bueny.labbayk.data.remote.QuranApiArabic
import de.bueny.labbayk.data.remote.QuranApiGerman
import de.bueny.labbayk.data.local.QuranVerseGerman

@Dao
interface QuranRepositoryInterface {
    suspend fun getChapter(surahNumber: Int): ChapterResponse
    suspend fun getQuranList(): List<ChapterListResponse>
    suspend fun getChapterGerman(): ChapterResponseGerman
    suspend fun getQuranListFromLocal(): List<QuranListEntity>
    suspend fun insertQuranListToLocal(quranList: List<ChapterListResponse>)
    suspend fun insertChapterToLocal(chapter: ChapterResponse)
    suspend fun insertGermanVersesToLocal(verses: QuranVerseGerman)
    suspend fun getGermanVerse(chapterNumber: Int): QuranVerseGerman
    suspend fun insertChapterAudiosToLocal(
        chapterId: Int,
        audios: Map<String, ChapterAudioResponse>
    )

    suspend fun getChapterCount(): Int
    suspend fun insertChapterArabic1ToLocal(chapterId: Int, arabic1: String)
    suspend fun getChapterArabic1(chapterId: Int): List<ChapterArabic1>
    suspend fun updateFavoriteStatus(id: Int, isFav: Boolean)
}

class QuranRepository(
    private val quranApi: QuranApiArabic,
    private val quranApiGerman: QuranApiGerman,
    private val quranListDao: QuranListDao,
    private val chapterGermanDao: ChapterGermanDao,
    private val chapterArabicDao: ChapterArabicDao
) : QuranRepositoryInterface {

    override suspend fun getChapter(surahNumber: Int): ChapterResponse {
        return quranApi.service.getChapter(surahNumber)
    }

    override suspend fun getQuranList(): List<ChapterListResponse> {
        return quranApi.service.getQuranList()
   }

    override suspend fun getQuranListFromLocal(): List<QuranListEntity> {
        return quranListDao.getAllQuranList()
    }

    override suspend fun insertQuranListToLocal(quranList: List<ChapterListResponse>) {
        val quranEntities = quranList.map { chapter ->
            QuranListEntity(
                surahName = chapter.surahName,
                surahNameArabic = chapter.surahNameArabic,
                surahNameArabicLong = chapter.surahNameArabicLong,
                surahNameTranslation = chapter.surahNameTranslation,
                revelationPlace = chapter.revelationPlace,
                totalAyah = chapter.totalAyah
            )
        }
        quranListDao.insertQuranList(quranEntities)
    }

    override suspend fun insertChapterToLocal(chapter: ChapterResponse) {
        val chapterEntity = ChapterEntity(
            surahName = chapter.surahName,
            surahNameArabic = chapter.surahNameArabic,
            surahNameArabicLong = chapter.surahNameArabicLong,
            surahNameTranslation = chapter.surahNameTranslation,
            revelationPlace = chapter.revelationPlace,
            totalAyah = chapter.totalAyah,
            surahNo = chapter.surahNo
        )

        chapterArabicDao.insertChapter(chapterEntity)
    }

    override suspend fun insertGermanVersesToLocal(verses: QuranVerseGerman) {
        chapterGermanDao.insertAll(listOf(verses))
    }

    override suspend fun getGermanVerse(chapterNumber: Int): QuranVerseGerman {
        return chapterGermanDao.getGermanVerse(chapterNumber)
    }

    override suspend fun insertChapterAudiosToLocal(
        chapterId: Int,
        audios: Map<String, ChapterAudioResponse>
    ) {
        val audioEntities = audios.map { entry ->
            ChapterAudio(
                chapterId = chapterId,
                reciter = entry.value.reciter,
                url = entry.value.url,
                originalUrl = entry.value.originalUrl
            )
        }
        audioEntities.forEach { chapterArabicDao.insertChapterAudios(it) }
    }

    override suspend fun getChapterCount(): Int {
        return chapterArabicDao.getChapterCount()
    }

    override suspend fun insertChapterArabic1ToLocal(chapterId: Int, arabic1: String) {
        val arabic1Entity = ChapterArabic1(
            chapterId = chapterId,
            arabic1 = arabic1
        )
        chapterArabicDao.insertChapterArabic1(arabic1Entity)
    }

    override suspend fun getChapterArabic1(chapterId: Int): List<ChapterArabic1> {
        return chapterArabicDao.getArabicVersesByChapterId(chapterId)
    }

    override suspend fun updateFavoriteStatus(id: Int, isFav: Boolean) {
        chapterGermanDao.updateFavoriteStatus(id, isFav)
    }

    override suspend fun getChapterGerman(): ChapterResponseGerman {
        return quranApiGerman.service.getChapter()
    }
}