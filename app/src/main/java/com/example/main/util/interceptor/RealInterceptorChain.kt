package com.example.main.util.interceptor

import java.io.IOException
class RealInterceptorChain(
    private var interceptors: List<Interceptor>?,
    private var index: Int,
    var request: Request?,
) : Interceptor.Chain{

    private var calls = 0

    @Throws(IOException::class)
    override fun proceed(
        request: Request?
    ): Response? {
        if (index >= interceptors!!.size) throw AssertionError()
        calls++


        // Call the next interceptor in the chain.
        val next = RealInterceptorChain(
            interceptors, index + 1, request)
        val interceptor = interceptors!![index]
        val response = interceptor.intercept(next)
            ?: throw NullPointerException("interceptor $interceptor returned null")

        // Confirm that the intercepted response isn't null.
        return response
    }

    override fun request(): Request? {
        return request
    }
}