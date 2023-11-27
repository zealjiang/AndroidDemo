package com.example.main.api

import com.example.network.apiresponse.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TecentNewsApiInterface {
    @GET("release/channel/envelop")
    suspend fun getNewsChannelsWithEnvelope(
/*      这里是公共请求，每个注解表示一个参数，这里加的Header,需要每个类都传参
        为了方便，这里使用了拦截器，在拦截器里加了公共参数
        @Header("Source") source: String,
        @Header("Authorization") authorization: String,
        @Header("Date") date: String,*/
    ): NetworkResponse<NewsChannelBean>

    @GET("release/channel/npe")
    suspend fun getNewsChannelsWithNpe(
    ): NetworkResponse<NewsChannelBean>
}