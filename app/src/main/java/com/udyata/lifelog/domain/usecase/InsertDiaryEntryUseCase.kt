package com.udyata.lifelog.domain.usecase

import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.data.repository.DiaryRepository
import com.udyata.lifelog.util.RequestState
import kotlinx.coroutines.flow.Flow

class InsertDiaryEntryUseCase(private val repository: DiaryRepository) {
    suspend fun execute(diaryEntry: DiaryEntry) = repository.insertDiaryEntry(diaryEntry)
}
