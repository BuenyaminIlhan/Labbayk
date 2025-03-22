package de.bueny.labbayk.data.local

import androidx.compose.runtime.MutableState
import androidx.room.Entity
import kotlinx.coroutines.flow.MutableStateFlow

@Entity(tableName = "quran_list", primaryKeys = ["surahName", "surahNameArabic"])
data class QuranListEntity(
    val surahName: String,
    val surahNameArabic: String,
    val surahNameArabicLong: String,
    val surahNameTranslation: String,
    val revelationPlace: String,
    val totalAyah: Int
)
