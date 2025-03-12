package de.bueny.labbayk.data.remote

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import de.bueny.labbayk.data.local.ChapterEntity

data class ChapterResponseGerman(
    val quran: Map<String, QuranVerseGermanResponse>
)

data class QuranVerseGermanResponse(
    val chapter: Int,
    val verse: Int,
    val text: String
)

@Entity(tableName = "quran_verse_german",
    foreignKeys = [ForeignKey(
        entity = ChapterEntity::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class QuranVerseGerman(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chapter: Int,
    val verse: Int,
    val text: String,
    val chapterId: Int
)
