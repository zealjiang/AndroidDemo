package com.example.network.apiresponse

import com.example.network.base.BaseResponse
import com.example.network.utils.MoshiUtils
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NetworkResponseCall<S: Any>(private val call: Call<S>): Call<NetworkResponse<S>>{
    override fun clone(): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call.clone())
    }

    override fun execute(): Response<NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun cancel() {
        return call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }

    override fun enqueue(callback: Callback<NetworkResponse<S>>) {
        return call.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                   if (body != null) {
                       callback.onResponse(this@NetworkResponseCall, Response.success(NetworkResponse.Success(body)))
                   } else {
                       callback.onResponse(this@NetworkResponseCall, Response.success(NetworkResponse.UnknownError(null)))
                   }
                } else {
                    if (error != null && error.contentLength() > 0) {
                        val errorResponse = MoshiUtils.fromJson<BaseResponse>(error.string(), BaseResponse::class.java)
                        callback.onResponse(this@NetworkResponseCall, Response.success(
                            NetworkResponse.ApiError(errorResponse?.showapiResError ?: "", errorResponse?.showapiResCode ?: -1)
                        ))
                    } else {
                        callback.onResponse(this@NetworkResponseCall, Response.success(
                            NetworkResponse.NetworkError(error?.string() ?: "message is empty", code)
                        ))
                    }
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> NetworkResponse.NetworkError(t.message.toString(), 400)
                    else -> NetworkResponse.UnknownError(t)
                }

                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

        })
    }

}