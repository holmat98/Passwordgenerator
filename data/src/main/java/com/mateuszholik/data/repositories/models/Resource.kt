package com.mateuszholik.data.repositories.models

sealed class Resource<T> {

    data class Success<T>(val data: T) : Resource<T>()
    class EmptyBody<T> : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}
