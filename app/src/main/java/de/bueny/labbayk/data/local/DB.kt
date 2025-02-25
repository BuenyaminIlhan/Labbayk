package de.bueny.labbayk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuranListEntity::class], version = 1, exportSchema = false)
abstract class QuranDatabase : RoomDatabase() {
    abstract val quranListDao: QuranListDao

    companion object {
        private var Instance: QuranDatabase? = null

        fun getDB(context: Context): QuranDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, QuranDatabase::class.java, "quran_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
