package com.mateuszholik.mvi.extensions

import com.mateuszholik.mvi.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

inline fun <T: UiState, reified R: UiState> MutableStateFlow<T>.updateState(
    update: R.() -> T,
) =
    update { currentState ->
        if (currentState is R) {
            currentState.update()
        } else {
            currentState
        }
    }
