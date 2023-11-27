package com.example.network

import com.example.network.apiresponse.NetworkResponseAdapterFactory
import com.example.network.base.INetworkRequiredInfo
import com.example.network.error.GlobalErrorHandler
import com.example.network.interceptor.CommonRequestInterceptor
import com.example.network.interceptor.CommonResponseInterceptor
import com.example.network.utils.TencentUtil
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class BaseNetworkApi {

    var mRetrofit: Retrofit ?= null
    private val globalErrorHandler = GlobalErrorHandler()
    companion object {
        val BASE_URL: String = "https://xxx.xx/"
        private var iNetworkRequiredInfo: INetworkRequiredInfo? = null

        fun init(networkRequiredInfo: INetworkRequiredInfo) {
            iNetworkRequiredInfo = networkRequiredInfo
        }

    }

    open fun getInterceptor(): Interceptor? {
        return null
    }

/*    private val moshi = Moshi.Builder()
        .add(MoshiResultTypeAdapterFactory(getEnvelopeHandler()))
        .addLast(KotlinJsonAdapterFactory())
        .build()*/

    open fun <T> getService(service: Class<T>?): T {
        return mRetrofit!!.create(service)
    }

    constructor(baseUrl: String) {
        val retrofitBuild = Retrofit.Builder()
        retrofitBuild.baseUrl(baseUrl)
        retrofitBuild.client(getOkHttpClient())
//        retrofitBuild.addConverterFactory(MoshiConverterFactory.create(moshi))
//        retrofitBuild.addCallAdapterFactory(NetworkResponseAdapterFactory(globalErrorHandler))
        mRetrofit = retrofitBuild.build()
    }

    private fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")

/*    suspend fun getTencentChannels(): Response<NewsChannelBean> {
        val retrofit: Retrofit
        val retrofitBuild = Retrofit.Builder()
        retrofitBuild.baseUrl(BASE_URL)
        retrofitBuild.client(getOkHttpClient())
        retrofitBuild.addConverterFactory(MoshiConverterFactory.create())
        retrofitBuild.addCallAdapterFactory(NetworkResponseAdapterFactory())
        retrofit = retrofitBuild.build()
        val timeStr = TencentUtil.getTimeStr()

        return retrofit.create(TecentNewsApiInterface::class.java)
            .getNewsChannels()

    }*/

    private fun getOkHttpClient(): OkHttpClient? {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(CommonRequestInterceptor(iNetworkRequiredInfo))
        okHttpClientBuilder.addInterceptor(CommonResponseInterceptor())
        if (iNetworkRequiredInfo != null && iNetworkRequiredInfo!!.isDebug) {
//            val httpLoggingInterceptor = HttpLoggingInterceptor()
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        } else {

        }

        return okHttpClientBuilder.build()
    }

}