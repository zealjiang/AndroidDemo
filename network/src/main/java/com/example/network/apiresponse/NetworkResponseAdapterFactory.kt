package com.example.network.apiresponse

import com.example.network.error.BusinessException
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<Foo> or Call<NetworkResponse<out Foo>>"
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            return object : CallAdapter<Any, Call<*>?> {
                override fun responseType(): Type {
                    return responseType
                }

                override fun adapt(call: Call<Any>): Call<*> {
                    return NetworkResponseCall(call)
                }
            }
        }

        return null
    }


    open interface ErrorHander {
        fun onFailure(throwable: BusinessException)
    }
}