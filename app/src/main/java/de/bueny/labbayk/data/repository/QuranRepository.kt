package de.bueny.labbayk.data.repository

import de.bueny.labbayk.data.remote.ChapterListResponse
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.data.remote.QuranApi


interface QuranRepositoryInterface {
    suspend fun getChapter(surahNumber: Int): ChapterResponse
    //suspend fun getChapterList(): ChapterListResponse
}

class QuranRepository(
    private val quranApi: QuranApi
) : QuranRepositoryInterface {

    override suspend fun getChapter(surahNumber: Int): ChapterResponse {
       return quranApi.service.getChapter(surahNumber)
    }

//    override suspend fun getChapterList(): ChapterListResponse {
//        TODO("Not yet implemented")
//    }
}