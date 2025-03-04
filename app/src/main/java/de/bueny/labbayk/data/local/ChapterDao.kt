package de.bueny.labbayk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: ChapterEntity)

    @Query("SELECT * FROM chapter WHERE surahNo = :surahNo")
    suspend fun getChapter(surahNo: Int): ChapterEntity?
}
