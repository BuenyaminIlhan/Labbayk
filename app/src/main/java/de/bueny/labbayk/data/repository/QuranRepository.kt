package de.bueny.labbayk.data.repository

import androidx.room.Dao
import de.bueny.labbayk.data.local.ChapterDao
import de.bueny.labbayk.data.local.ChapterEntity
import de.bueny.labbayk.data.local.QuranListDao
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.data.remote.ChapterListResponse
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApi

@Dao
interface QuranRepositoryInterface {
    suspend fun getChapter(surahNumber: Int): ChapterResponse
    suspend fun getQuranList(): List<ChapterListResponse>
    suspend fun getQuranListFromLocal(): List<QuranListEntity>
    suspend fun insertQuranListToLocal(quranList: List<ChapterListResponse>)
    suspend fun insertChapterToLocal(chapter: ChapterResponse)
}

class QuranRepository(
    private val quranApi: QuranApi,
    private val quranListDao: QuranListDao,
    private val chapterDao: ChapterDao
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

        chapterDao.insertChapter(chapterEntity)

    }
}