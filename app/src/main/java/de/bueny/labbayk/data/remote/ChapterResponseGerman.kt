package de.bueny.labbayk.data.remote

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import de.bueny.labbayk.data.local.ChapterEntity

data class ChapterResponseGerman(
    val quran: List<QuranVerseGermanResponse>
)

data class QuranVerseGermanResponse(
    val chapter: Int,
    val verse: Int,
    val text: String
)