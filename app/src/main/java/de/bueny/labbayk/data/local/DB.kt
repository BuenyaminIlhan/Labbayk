package de.bueny.labbayk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.bueny.labbayk.data.local.QuranVerseGerman

@Database(
    entities = [
        QuranListEntity::class,
        ChapterEntity::class,
        ChapterAudio::class,
        ChapterArabic1::class,
        QuranVerseGerman::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuranDatabase : RoomDatabase() {
    abstract val quranListDao: QuranListDao
    abstract val chapterArabicDao: ChapterArabicDao
    abstract val chapterGermanDao: ChapterGermanDao

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