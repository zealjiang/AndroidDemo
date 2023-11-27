package com.example.main.api

import com.example.network.base.BaseResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsChannelBean(
    @Json(name = "showapi_res_body")
    val showapiResBody: ShowapiResBody
): BaseResponse()


@JsonClass(generateAdapter = true)
data class ShowapiResBody(
    @Json(name = "channelList")
    val channelList: List<Channel>,
    @Json(name = "ret_code")
    val retCode: Int,
    @Json(name = "totalNum")
    val totalNum: Int
)

@JsonClass(generateAdapter = true)
data class Channel(
    @Json(name = "channelId")
    val channelId: String,
    @Json(name = "name")
    val name: String
)