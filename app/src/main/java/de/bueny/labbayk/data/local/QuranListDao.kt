package de.bueny.labbayk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuranListDao {

    @Query("SELECT * FROM quran_list")
    suspend fun getAllQuranList(): List<QuranListEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertQuranList(quranList: List<QuranListEntity>)
}