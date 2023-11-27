package com.example.network

import okhttp3.Interceptor

object HttpbinOrgNetworkApi : BaseNetworkApi("https://httpbin.org/"){
    override fun getInterceptor(): Interceptor? {
        return null
    }
}