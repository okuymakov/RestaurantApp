package com.example.domain.network

sealed interface Response<out T : Any> {
    data class Success<out T : Any>(val data: T) : Response<T>

    sealed interface Failure : Response<Nothing> {
        data class NoInternetError(val exception: Exception) : Failure
        data class ApiError(val exception: Exception) : Failure
        data class UnknownError(val exception: Exception) : Failure
    }
}
