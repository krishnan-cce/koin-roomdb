package com.udyata.lifelog.domain.usecase

import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.data.repository.DiaryRepository
import com.udyata.lifelog.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetAllDiaryEntriesUseCase(private val repository: DiaryRepository) {
    fun execute(): Flow<List<DiaryEntry>> = repository.getAllDiaryEntries()
}
