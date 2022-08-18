package com.ahmed.shortener.data

sealed class ApiRequestState<out T> {
    object Loading : ApiRequestState<Nothing>()
    data class Fail(val message: String) : ApiRequestState<Nothing>()
    data class Success<T>(val data: T) : ApiRequestState<T>()
}
