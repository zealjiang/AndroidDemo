package com.example.main.util

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.api.TecentNewsApiInterface
import com.example.main.api.TencentNetworkWithEnvelopeApi
import com.example.network.apiresponse.NetworkResponse
import com.example.network.utils.MoshiUtils
import kotlinx.coroutines.launch

class NetworkTest: ViewModel() {

    fun onGetChannelsClicked() {
        viewModelScope.launch {
            val result = TencentNetworkWithEnvelopeApi.getService(TecentNewsApiInterface::class.java).getNewsChannelsWithEnvelope()
/*            when (result) {
                is NetworkResponse.ApiError -> {
                    Log.e("NET", MoshiUtils.toJson(result.code()))
                }
                is NetworkResponse.NetworkError -> {
                    Log.e("NET", MoshiUtils.toJson(result.code()))
                }
                is NetworkResponse.Success -> {
                    Log.e("NET", MoshiUtils.toJson(result.body()))
                }
                is NetworkResponse.UnknownError -> {
                    Log.e("NET", MoshiUtils.toJson(result.error?.message))
                }
            }*/
        }
    }

    fun onGetChannelsWithKotlinNpeClicked() {
        viewModelScope.launch {
            val result = TencentNetworkWithEnvelopeApi.getService(TecentNewsApiInterface::class.java).getNewsChannelsWithNpe()
/*            when (result) {
                is NetworkResponse.ApiError -> {
                    Log.e("NET", MoshiUtils.toJson(result.code()))
                }
                is NetworkResponse.NetworkError -> {
                    Log.e("NET", MoshiUtils.toJson(result.code()))
                }
                is NetworkResponse.Success -> {
                    Log.e("NET", MoshiUtils.toJson(result.body()))
                }
                is NetworkResponse.UnknownError -> {
                    Log.e("NET", MoshiUtils.toJson(result.error?.message))
                }
            }*/
        }
    }
}