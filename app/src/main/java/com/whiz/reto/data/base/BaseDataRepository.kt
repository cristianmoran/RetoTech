package com.whiz.reto.data.base

import com.whiz.core.network.EventResult


abstract class BaseDataRepository {

    fun <T> generateResponseError(failure: com.whiz.core.network.EventResult.Failure): com.whiz.core.network.EventResult<T> {
        return  com.whiz.core.network.EventResult.Failure(
            type = failure.type,
            responseError = failure.responseError
        )
    }
}