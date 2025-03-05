package de.bueny.labbayk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChapter(chapter: ChapterEntity)

    @Query("SELECT COUNT(*) FROM chapter")
    suspend fun getChapterCount(): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChapterAudios(audios: ChapterAudio)

    @Query("SELECT * FROM chapter WHERE surahNo = :surahNo")
    suspend fun getChapter(surahNo: Int): ChapterEntity?
}
