package com.example.main

import android.app.Application
import com.example.network.base.INetworkRequiredInfo

class MNetwork(var mApplication: Application): INetworkRequiredInfo {

    override fun getAppVersionName(): String {
        return BuildConfig.VERSION_NAME
    }
    override fun getAppVersionCode(): String {
        return BuildConfig.VERSION_CODE.toString()
    }
    override fun isDebug(): Boolean {
       return BuildConfig.DEBUG
    }
    override fun getApplicationContext(): Application {
        return mApplication
    }
}