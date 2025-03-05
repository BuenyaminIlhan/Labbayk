package de.bueny.labbayk.data.repository

import androidx.room.Dao
import de.bueny.labbayk.data.local.ChapterAudio
import de.bueny.labbayk.data.local.ChapterDao
import de.bueny.labbayk.data.local.ChapterEntity
import de.bueny.labbayk.data.local.QuranListDao
import de.bueny.labbayk.data.local.QuranListEntity
import de.bueny.labbayk.data.remote.ChapterAudioResponse
import de.bueny.labbayk.data.remote.ChapterListResponse
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApi

@Dao
interface QuranRepositoryInterface {
    suspend fun getChapter(surahNumber: Int): ChapterResponse
    suspend fun getQuranList(): List<ChapterListResponse>
    suspend fun getQuranListFromLocal(): List<QuranListEntity>
    //suspend fun getChapterAudios(surahNumber: Int): List<ChapterAudioResponse>
    suspend fun insertQuranListToLocal(quranList: List<ChapterListResponse>)
    suspend fun insertChapterToLocal(chapter: ChapterResponse)
    suspend fun insertChapterAudiosToLocal(chapterId: Int, audios: Map<String, ChapterAudioResponse>)
    suspend fun getChapterCount(): Int

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

//    override suspend fun getChapterAudios(surahNumber: Int): List<ChapterAudioResponse> {
//        return quranApi.service.getChapterAudios(surahNumber)
//    }

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



  override suspend fun insertChapterAudiosToLocal(chapterId: Int, audios: Map<String, ChapterAudioResponse>) {
        // Map wird in eine Liste umgewandelt, um sie in die Datenbank zu speichern
        val audioEntities = audios.map { entry ->
            ChapterAudio(
                chapterId = chapterId,  // Verknüpfung mit Kapitel
                reciter = entry.value.reciter,
                url = entry.value.url,
                originalUrl = entry.value.originalUrl
            )
        }
        audioEntities.forEach { chapterDao.insertChapterAudios(it) }
    }


    override suspend fun getChapterCount(): Int {
        return chapterDao.getChapterCount()
    }
}