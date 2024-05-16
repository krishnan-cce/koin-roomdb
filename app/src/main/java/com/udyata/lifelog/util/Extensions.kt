package com.udyata.lifelog.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


fun <T> collectAndSetState(
    stateFlow: MutableStateFlow<RequestState<T>>,
    flow: Flow<T>,
    scope: CoroutineScope
) {
    scope.launch {
        stateFlow.value = RequestState.Loading
        try {
            flow.collectLatest { response ->
                stateFlow.value = RequestState.Success(response)
            }
        } catch (e: Exception) {
            stateFlow.value = RequestState.Error(e.message ?: "Unknown Error")
        }
    }
}