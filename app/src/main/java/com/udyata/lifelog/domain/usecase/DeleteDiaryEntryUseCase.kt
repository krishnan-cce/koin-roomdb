package com.udyata.lifelog.domain.usecase

import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.data.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow

class DeleteDiaryEntryUseCase(private val repository: DiaryRepository) {
    suspend fun execute(diaryEntry: DiaryEntry) = repository.deleteDiaryEntry(diaryEntry)
}