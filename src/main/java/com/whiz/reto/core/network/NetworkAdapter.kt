package com.whiz.reto.core.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.whiz.reto.domain.entity.ErrorRetrofitType
import com.whiz.reto.core.util.isAirplaneModeActive
import com.whiz.reto.core.util.isConnectedRed
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

sealed class EventResult<out T> {
    data class Success<T>(val data: T?) : EventResult<T>()
    data class Failure(val type: ErrorRetrofitType, val responseError: ErrorResponse?) :
        EventResult<Nothing>()
}

class MyCallAdapterFactory(val context: Context) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                EventResult::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType, context)
                }
                else -> null
            }
        }
        else -> null
    }
}

class ResultAdapter(
    private val type: Type,
    private val context: Context
) : CallAdapter<Type, Call<EventResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<EventResult<Type>> =
        ResultCall(call, context)
}

class ResultCall<T>(proxy: Call<T>, val context: Context) : CallDelegate<T, EventResult<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<EventResult<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val code = response.code()
                val responseErrorString = response.errorBody()?.string()
                val result = if (code in 200..300) {
                    val body = response.body()
                    EventResult.Success(body)
                } else {
                    try {
                        val responseError =
                            GsonBuilder().create().fromJson(
                                responseErrorString, ErrorResponse::class.java
                            )
                        formatResultException(code = code, responseError)
                    } catch (e: Exception) {
                        formatResultException()
                    }
                }
                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = formatResultException()
                callback.onResponse(this@ResultCall, Response.success(result))
            }
        })

    override fun cloneImpl() = ResultCall(proxy.clone(), context)
    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }

    fun formatResultException(
        code: Int = 0,
        responseError: ErrorResponse? = null
    ): EventResult.Failure {
        return if (isAirplaneModeActive(context)) {
            EventResult.Failure(ErrorRetrofitType.AIRPLANE_ACTIVE, responseError)
        } else if (!isConnectedRed(context)) {
            EventResult.Failure(ErrorRetrofitType.NETWORK_EXCEPTION, responseError)
        } else if (code == 401) {
            EventResult.Failure(ErrorRetrofitType.UNAUTHORIZED, responseError)
        } else {
            EventResult.Failure(ErrorRetrofitType.EXCEPTION, responseError)
        }
    }
}

abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    override final fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    override final fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

data class ErrorResponse(
    val apiEstado: String?,
    val apiMensaje: String?,
    val title: String?,
    val detail: String?
)