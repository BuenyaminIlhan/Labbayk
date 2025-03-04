package de.bueny.labbayk.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.bueny.labbayk.data.local.AudioInfo

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAudioMap(value: Map<String, AudioInfo>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAudioMap(value: String): Map<String, AudioInfo> {
        val type = object : TypeToken<Map<String, AudioInfo>>() {}.type
        return gson.fromJson(value, type)
    }
}


