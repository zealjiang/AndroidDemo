package com.example.routers

import android.util.Log
import com.example.router.SimpleRouter

object RouterRegister {

    fun init() {
        Log.w("Register","main module")
        SimpleRouter.putRoute("/main/MVVMActivity","com.example.main.MVVMActivity")
        SimpleRouter.putRoute("/main/SunriseActivity","com.example.main.SunriseActivity")
        SimpleRouter.putRoute("/takeout/MainActivity","com.example.takeout.MainActivity")
    }
}