package de.bueny.labbayk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.bueny.labbayk.data.remote.AudioInfo

//@Entity(tableName = "chapter", primaryKeys = ["surahName", "surahNameArabic", "surahNo"])

@Entity(tableName = "chapter")
data class ChapterEntity(
    @PrimaryKey val id: Int,
    val surahName: String,
    val surahNameArabic: String,
    val surahNameArabicLong: String,
    val surahNameTranslation: String,
    val revelationPlace: String,
    val totalAyah: Int,
    val surahNo: Int,

    //val audioInfo: Map<String, AudioInfo>,
//    val english: List<String>,
//    val arabic1: List<String>,
//    val arabic2: List<String>,
//    val bengali: List<String>
)

data class AudioInfo(
    val reciter: String,
    val url: String,
    val originalUrl: String
)
