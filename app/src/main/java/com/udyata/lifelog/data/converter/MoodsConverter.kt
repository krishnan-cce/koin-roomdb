package com.udyata.lifelog.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udyata.lifelog.data.local.Moods

class MoodsConverter {

    @TypeConverter
    fun fromMoods(moods: Moods): String {
        return Gson().toJson(moods)
    }

    @TypeConverter
    fun toMoods(moodsString: String): Moods {
        val type = object : TypeToken<Moods>() {}.type
        return Gson().fromJson(moodsString, type)
    }
}
