package com.mateuszholik.domain.models

sealed class State<T> {

    class Success<T>(val data: T) : State<T>()
    class Error<T>(val error: ErrorType) : State<T>()
    class EmptyBody<T> : State<T>()
}
