package com.udyata.lifelog.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.domain.dao.DiaryEntryDao

@Database(entities = [DiaryEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryEntryDao(): DiaryEntryDao
}
