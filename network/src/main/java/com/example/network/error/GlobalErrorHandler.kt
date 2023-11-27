package com.example.network.error

import com.example.network.apiresponse.NetworkResponseAdapterFactory

class GlobalErrorHandler : NetworkResponseAdapterFactory.ErrorHander{
    override fun onFailure(throwable: BusinessException) {
        when (throwable.code) {
            2096 -> {

            }
            3099 -> {}
        }
    }
}