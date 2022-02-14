package com.backbase.assignment.ui.data.model

sealed class MovieResult<out T: Any> {
    data class Success<out T: Any>(val data: T) : MovieResult<T>()
    data class Error(val error: Exception) : MovieResult<Nothing>()
}