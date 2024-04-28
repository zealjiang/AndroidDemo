package com.example.main.util.interceptor

import android.util.Log

class CallServerInterceptor : Interceptor{
    val TAG = "CallServerInterceptor"
    override fun intercept(chain: Interceptor.Chain?): Response? {
        Log.d("Interceptor", "$TAG intercept")
        var request = chain?.request()
        Log.d("Interceptor", "$TAG request")
        var response = Response()
        Log.d("Interceptor", "$TAG proceed")
        return response
    }
}