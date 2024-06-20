package com.whiz.reto.data.base

import com.whiz.reto.core.network.EventResult


abstract class BaseDataRepository {

    fun <T> generateResponseError(failure: EventResult.Failure): EventResult<T> {
        return  EventResult.Failure(
            type = failure.type,
            responseError = failure.responseError
        )
    }
}