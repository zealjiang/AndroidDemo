package com.example.main.util.interceptor

import android.util.Log

class InterceptorA : Interceptor{
    val TAG = "InterceptorA"
    override fun intercept(chain: Interceptor.Chain?): Response? {
        Log.d("Interceptor", "$TAG intercept")
        var request = chain?.request()
        Log.d("Interceptor", "$TAG request")
        var response = chain?.proceed(request)
        Log.d("Interceptor", "$TAG proceed")
        return response
    }
}