package com.example.main.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings


object LocationUtil {

    /**
     * 判断wifi是否开启
     */
    fun isWifiOpen(context: Context): Boolean {

        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager? ?: return false

        return wifiManager.isWifiEnabled
    }

    /**
     * 打开wifi设置界面
     */
    fun openWifiSettingPage(activity: Activity) {

        //android 10以后不能直接打开wifi
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val wifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
            wifiManager?.setWifiEnabled(true)
        } else {
            val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
            activity.startActivityForResult(panelIntent, 111)
        }
    }

    //---------------------- gps -----------------------------
    fun checkGPSIsOpen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun goToGpsSetting(context: Activity) {
        //跳转GPS设置界面
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivityForResult(intent, 112)
    }
}