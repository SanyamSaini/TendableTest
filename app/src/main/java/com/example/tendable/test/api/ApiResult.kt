package com.example.tendable.test.api

import com.example.tendable.test.model.ApiResponse


sealed class ApiResult<T>(
    data: T? = null,
    exception: Exception? = null
) {
    data class Success<T>(val data: T) : ApiResult<T>(data, null)

    data class Error<T>(
        val exception: Exception, val apiResponse: ApiResponse? = null
    ) : ApiResult<T>(null, exception)

}