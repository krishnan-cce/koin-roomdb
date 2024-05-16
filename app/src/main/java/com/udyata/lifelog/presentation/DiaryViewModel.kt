package com.udyata.lifelog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.data.local.Moods
import com.udyata.lifelog.data.repository.DiaryRepository
import com.udyata.lifelog.domain.usecase.DeleteDiaryEntryUseCase
import com.udyata.lifelog.domain.usecase.GetAllDiaryEntriesUseCase
import com.udyata.lifelog.domain.usecase.InsertDiaryEntryUseCase
import com.udyata.lifelog.util.RequestState
import com.udyata.lifelog.util.collectAndSetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class DiaryViewModel(
    private val getAllDiaryEntriesUseCase: GetAllDiaryEntriesUseCase,
    private val insertDiaryEntryUseCase: InsertDiaryEntryUseCase,
    private val deleteDiaryEntryUseCase: DeleteDiaryEntryUseCase
) : ViewModel() {


    private val _diaryEntriesState: MutableStateFlow<RequestState<List<DiaryEntry>>> = MutableStateFlow(RequestState.Idle)
    val diaryEntriesState: StateFlow<RequestState<List<DiaryEntry>>> = _diaryEntriesState.asStateFlow()

    private val _operationState = MutableStateFlow<RequestState<Unit>>(RequestState.Idle)
    val operationState: StateFlow<RequestState<Unit>> = _operationState

    private val _insertDairyFormState = MutableStateFlow(InsertDairyEntryState())
    val insertDairyFormState: StateFlow<InsertDairyEntryState> = _insertDairyFormState.asStateFlow()


    init {
        Timber.d("DiaryViewModel Initialized")
        fetchAllDiaryEntries()
    }


    private fun fetchAllDiaryEntries() {
        collectAndSetState(_diaryEntriesState, getAllDiaryEntriesUseCase.execute(), viewModelScope)
    }


//    private fun insertDiaryEntry(diaryEntry: DiaryEntry) {
//        viewModelScope.launch {
//            _operationState.value = RequestState.Loading
//            try {
//                val result = insertDiaryEntryUseCase.execute(diaryEntry)
//                _operationState.value = result
//                if (result is RequestState.Success) {
//                    fetchAllDiaryEntries()
//                    Timber.d(result.message ?: "Diary entry inserted successfully")
//                }
//            } catch (e: Exception) {
//                _operationState.value = RequestState.Error(e.message ?: "Unknown Error")
//            }
//        }
//    }
//
//    fun deleteDiaryEntry(diaryEntry: DiaryEntry) {
//        viewModelScope.launch {
//            _operationState.value = RequestState.Loading
//            try {
//                val result = deleteDiaryEntryUseCase.execute(diaryEntry)
//                _operationState.value = result
//                if (result is RequestState.Success) {
//                    fetchAllDiaryEntries()
//                    Timber.d(result.message ?: "Diary entry deleted successfully")
//                }
//            } catch (e: Exception) {
//                _operationState.value = RequestState.Error(e.message ?: "Unknown Error")
//            }
//        }
//    }

    fun onInsertDairyEvent(event: InsertDairyEntryEvent){

        when(event){
            InsertDairyEntryEvent.Submit -> TODO()
            is InsertDairyEntryEvent.SetContent ->  { updateState { it.copy(content = event.content) } }
            is InsertDairyEntryEvent.SetContentErr -> { updateState { it.copy(contentErr = event.contentErr) } }
            is InsertDairyEntryEvent.SetDateTime -> { updateState { it.copy(dateTime = event.dateTime) } }
            is InsertDairyEntryEvent.SetDateTimeErr -> { updateState { it.copy(dateTimeErr = event.dateTimeErr) } }
            is InsertDairyEntryEvent.SetDocumentUrl -> { updateState { it.copy(documentUrl = event.documentUrl) } }
            is InsertDairyEntryEvent.SetDocumentUrlErr -> { updateState { it.copy(documentUrlErr = event.documentUrlErr) } }
            is InsertDairyEntryEvent.SetImageUrl -> { updateState { it.copy(imageUrl = event.imageUrl) } }
            is InsertDairyEntryEvent.SetImageUrlErr -> { updateState { it.copy(imageUrlErr = event.imageUrlErr) } }
            is InsertDairyEntryEvent.SetLocation -> { updateState { it.copy(location = event.location) } }
            is InsertDairyEntryEvent.SetLocationErr -> { updateState { it.copy(locationErr = event.locationErr) } }
            is InsertDairyEntryEvent.SetMood -> { updateState { it.copy(mood = event.mood) } }
            is InsertDairyEntryEvent.SetMoodErr -> { updateState { it.copy(moodErr = event.moodErr) } }
            is InsertDairyEntryEvent.SetTitle -> { updateState { it.copy(title = event.title) } }
            is InsertDairyEntryEvent.SetTitleErr -> { updateState { it.copy(titleErr = event.titleErr) } }
        }
    }

    private inline fun updateState(update: (InsertDairyEntryState) -> InsertDairyEntryState) {
        _insertDairyFormState.value = update(_insertDairyFormState.value)
    }

    fun insertHardcodedDiaryEntries() {
        val entry1 = DiaryEntry(
            title = "A Beautiful Day",
            content = "Today was a beautiful day! I went to the park and enjoyed the sunshine.",
            location = "Central Park",
            date = System.currentTimeMillis(),
            mood = Moods.getMoodsList()[0],
            imageUri = "null",
            documentUri = "null"
        )

        val entry2 = DiaryEntry(
            title = "A Tough Day",
            content = "Today was really tough. Work was stressful, and I couldn't get much done.",
            location = "Home",
            date = System.currentTimeMillis(),
            mood = Moods.getMoodsList()[1],
            imageUri = "null",
            documentUri = "null"
        )

//        insertDiaryEntry(entry1)
//        insertDiaryEntry(entry2)
    }


}
