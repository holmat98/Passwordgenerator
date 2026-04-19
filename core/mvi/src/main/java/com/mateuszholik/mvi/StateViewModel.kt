package com.mateuszholik.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StateViewModel<State : UiState, Event : UiEvent, Action : com.mateuszholik.mvi.Action> :
    ViewModel() {

    abstract val uiState: StateFlow<State>

    abstract val uiEvent: SharedFlow<Event>

    abstract fun performAction(action: Action)

    protected val commonExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onCommonException(throwable)
    }

    protected abstract fun onCommonException(throwable: Throwable)
}
