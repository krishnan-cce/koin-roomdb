package com.udyata.lifelog.data.repository

import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.util.RequestState
import kotlinx.coroutines.flow.Flow


interface DiaryRepository {
    fun getAllDiaryEntries(): Flow<List<DiaryEntry>>
    suspend fun insertDiaryEntry(diaryEntry: DiaryEntry): RequestState<Unit>
    suspend fun deleteDiaryEntry(diaryEntry: DiaryEntry): RequestState<Unit>
}
