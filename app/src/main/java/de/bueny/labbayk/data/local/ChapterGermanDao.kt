package de.bueny.labbayk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterGermanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(verses: List<QuranVerseGerman>)

    @Query("SELECT * FROM quran_verse_german WHERE id = :chapterId")
    suspend fun getGermanVerse(chapterId: Int): QuranVerseGerman

    @Query("UPDATE quran_verse_german SET isFav = :isFav WHERE id = :verseId")
    suspend fun updateFavoriteStatus(verseId: Int, isFav: Boolean)

    @Query("SELECT * FROM quran_verse_german WHERE isFav = 1")
    fun getFavoriteVerses(): Flow<List<QuranVerseGerman>>

}