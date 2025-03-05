package de.bueny.labbayk.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(tableName = "chapter", primaryKeys = ["surahName", "surahNameArabic", "surahNo"])

@Entity(tableName = "chapter")
data class ChapterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val surahName: String,
    val surahNameArabic: String,
    val surahNameArabicLong: String,
    val surahNameTranslation: String,
    val revelationPlace: String,
    val totalAyah: Int,
    val surahNo: Int,
)

@Entity(
    tableName = "chapter_audios",
    foreignKeys = [ForeignKey(
        entity = ChapterEntity::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ChapterAudio(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val reciter: String,
    val url: String,
    val originalUrl: String,
    val chapterId: Int
)

@Entity(
    tableName = "chapter_arabic1",
    foreignKeys = [ForeignKey(
        entity = ChapterEntity::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ChapterArabic1(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val arabic1: List<String>,
    val chapterId: Int
)