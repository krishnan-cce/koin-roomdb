package com.udyata.lifelog.data.local

import com.udyata.lifelog.R
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class Moods(
    val id: Int,
    val image: Int,
    val name:String
){
    companion object {
        fun getMoodsList():List<Moods>{
            return listOf(
                Moods(1, R.drawable.ic_happy,"Happy"),
                Moods(2, R.drawable.ic_sad,"Sad"),
                Moods(3, R.drawable.ic_angry,"Angry"),
                Moods(4, R.drawable.ic_hearts,"Hearts"),
                Moods(5, R.drawable.ic_kiss,"Kiss"),
                Moods(6, R.drawable.ic_love,"Love"),
                Moods(7, R.drawable.ic_excited,"Excited")
            )
        }
    }
}