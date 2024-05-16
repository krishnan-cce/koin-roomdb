package com.udyata.lifelog.data.local

import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.udyata.lifelog.R
import com.udyata.lifelog.data.converter.MoodsConverter

@Entity(tableName = "diary_entries")
@TypeConverters(MoodsConverter::class)
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val location:String,
    val date: Long,
    val mood: Moods,
    val imageUri: String? = null,
    val documentUri: String? = null
)
