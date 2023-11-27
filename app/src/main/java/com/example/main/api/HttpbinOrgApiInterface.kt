package com.example.main.api

import com.example.network.apiresponse.NetworkResponse
import retrofit2.http.GET

interface HttpbinOrgApiInterface {

    @GET("status/404")
    suspend fun status404(): NetworkResponse<HttpbinOrgBaseResponse>

    @GET("status/501")
    suspend fun status01(): NetworkResponse<HttpbinOrgBaseResponse>
}