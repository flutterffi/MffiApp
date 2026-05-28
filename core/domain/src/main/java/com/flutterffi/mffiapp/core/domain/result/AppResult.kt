package com.flutterffi.mffiapp.core.domain.result

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val message: String, val cause: Throwable? = null) : AppResult<Nothing>
}

suspend inline fun <T> runAppCatching(block: suspend () -> T): AppResult<T> {
    return try {
        AppResult.Success(block())
    } catch (error: Throwable) {
        AppResult.Error(message = error.message ?: "Unknown error", cause = error)
    }
}
