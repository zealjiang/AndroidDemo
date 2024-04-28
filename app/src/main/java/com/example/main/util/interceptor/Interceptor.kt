package com.example.main.util.interceptor

import java.io.IOException
interface Interceptor {
    @Throws(IOException::class)
    fun intercept(chain: Chain?): Response?


    interface Chain {
        fun request(): Request?

        @Throws(IOException::class)
        fun proceed(request: Request?): Response?
    }
}