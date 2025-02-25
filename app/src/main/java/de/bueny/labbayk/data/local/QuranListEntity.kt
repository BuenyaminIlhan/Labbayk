package de.bueny.labbayk.data.local

import androidx.room.Entity

@Entity(tableName = "quran_list", primaryKeys = ["surahName", "surahNameArabic"])
data class QuranListEntity(
    val surahName: String,
    val surahNameArabic: String,
    val surahNameArabicLong: String,
    val surahNameTranslation: String,
    val revelationPlace: String,
    val totalAyah: Int
)
