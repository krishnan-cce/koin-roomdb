package com.udyata.lifelog.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.util.RequestState
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryEntryDao {

    @Query("SELECT * FROM diary_entries ORDER BY date DESC")
    fun getAllDiaryEntries(): Flow<List<DiaryEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diaryEntry: DiaryEntry)

    @Delete
    suspend fun delete(diaryEntry: DiaryEntry)
}
