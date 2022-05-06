package com.mateuszholik.domain.models

sealed class State<T> {

    data class Success<T>(val data: T) : State<T>()
    data class Error<T>(val error: ErrorState) : State<T>()
    class EmptyBody<T> : State<T>()
}
