package de.bueny.labbayk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.MutableStateFlow

@Entity(tableName = "quran_verse_german")
data class QuranVerseGerman(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chapter: Int,
    val verse: Int,
    val text: String,
    val isFav: Boolean = false
)