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

@Entity(tableName = "quran_verse_german")
data class QuranVerseGerman(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chapter: Int,
    val verse: Int,
    val text: String,
)

