package com.example.main.util.interceptor

import java.io.IOException


class RealCall {

    private var originalRequest: Request? = null
    fun execute() {
        originalRequest = Request()
        getResponseWithInterceptorChain()
    }

    @Throws(IOException::class)
    private fun getResponseWithInterceptorChain(): Response? {
        // Build a full stack of interceptors.
        val interceptors: MutableList<Interceptor> = ArrayList<Interceptor>()
        interceptors.add(InterceptorA())
        interceptors.add(InterceptorB())
        interceptors.add(InterceptorC())
        interceptors.add(CallServerInterceptor())
        val chain: Interceptor.Chain = RealInterceptorChain(
            interceptors,0,
            originalRequest,
        )
        val response: Response? = chain.proceed(originalRequest)
        return response
    }
}