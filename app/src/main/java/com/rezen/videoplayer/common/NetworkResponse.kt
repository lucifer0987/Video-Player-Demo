package com.rezen.videoplayer.common

sealed class NetworkResponse<T>(
    val data: T? = null,
    val error: Any? = null
) {
    class Success<T>(data: T? = null) : NetworkResponse<T>(data)
    class Error<T>(error: Any?) : NetworkResponse<T>(null, error)
}