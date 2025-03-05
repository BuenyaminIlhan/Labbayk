package de.bueny.labbayk.data.remote

data class ChapterResponse(
    val surahName: String,
    val surahNameArabic: String,
    val surahNameArabicLong: String,
    val surahNameTranslation: String,
    val revelationPlace: String,
    val totalAyah: Int,
    val surahNo: Int,
    val audio: Map<String, ChapterAudioResponse>,
    val english: List<String>,
    val arabic1: List<String>,
    val arabic2: List<String>,
    val bengali: List<String>
)

data class ChapterAudioResponse(
    val reciter: String,
    val url: String,
    val originalUrl: String
)

