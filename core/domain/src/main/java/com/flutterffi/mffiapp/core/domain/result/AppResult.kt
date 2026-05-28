package com.flutterffi.mffiapp.core.domain.result

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val message: String, val cause: Throwable? = null) : AppResult<Nothing>
}

inline fun <T> runAppCatching(block: () -> T): AppResult<T> {
    return runCatching(block).fold(
        onSuccess = { AppResult.Success(it) },
        onFailure = { AppResult.Error(message = it.message ?: "Unknown error", cause = it) },
    )
}
