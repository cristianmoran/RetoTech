package com.whiz.data.base

import com.whiz.domain.network.EventResult


abstract class BaseDataRepository {

    fun <T> generateResponseError(failure: EventResult.Failure): EventResult<T> {
        return  EventResult.Failure(
            type = failure.type,
            responseError = failure.responseError
        )
    }
}