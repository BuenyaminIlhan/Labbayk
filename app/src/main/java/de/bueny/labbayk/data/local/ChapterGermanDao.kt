package de.bueny.labbayk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.bueny.labbayk.data.remote.QuranVerseGerman

@Dao
interface ChapterGermanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(verses: List<QuranVerseGerman>)

    @Query("SELECT * FROM quran_verse_german WHERE chapter = :chapterNumber")
    suspend fun getAllGermanVerses(chapterNumber: Int): List<QuranVerseGerman>
}