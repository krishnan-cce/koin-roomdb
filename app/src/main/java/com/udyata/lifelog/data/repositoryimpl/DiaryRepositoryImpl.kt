package com.udyata.lifelog.data.repositoryimpl

import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.data.repository.DiaryRepository
import com.udyata.lifelog.domain.dao.DiaryEntryDao
import com.udyata.lifelog.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DiaryRepositoryImpl(private val diaryEntryDao: DiaryEntryDao) : DiaryRepository {

    override fun getAllDiaryEntries(): Flow<List<DiaryEntry>> {
        return diaryEntryDao.getAllDiaryEntries()
    }

    override suspend fun insertDiaryEntry(diaryEntry: DiaryEntry): RequestState<Unit> {
        return try {
            diaryEntryDao.insert(diaryEntry)
            RequestState.Success(Unit, message = "Diary entry inserted successfully")
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown Error")
        }
    }

    override suspend fun deleteDiaryEntry(diaryEntry: DiaryEntry): RequestState<Unit> {
        return try {
            diaryEntryDao.delete(diaryEntry)
            RequestState.Success(Unit, message = "Diary entry deleted successfully")
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown Error")
        }
    }
}
