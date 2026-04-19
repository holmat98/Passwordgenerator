package com.mateuszholik.domainbase.models

import kotlinx.coroutines.CancellationException

sealed interface Result<T> {

    data class Success<T>(val data: T) : Result<T>

    data class Error<T>(val exception: Exception) : Result<T>

    companion object {
        suspend fun <T> runCatching(invoke: suspend () -> Result<T>): Result<T> =
            try {
                invoke()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Error(e)
            }
    }
}
