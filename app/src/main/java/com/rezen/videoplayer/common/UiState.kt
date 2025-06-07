package com.rezen.videoplayer.common

sealed class UiState<T>(
    val data: T? = null,
    val error: Any? = null
) {
    class Success<T>(data: T? = null) : UiState<T>(data)
    class Loading<T>(data: T? = null) : UiState<T>(data)
    class Error<T>(error: Any?) : UiState<T>(null, error)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
            is Loading<T> -> "Loading"
        }
    }
}
