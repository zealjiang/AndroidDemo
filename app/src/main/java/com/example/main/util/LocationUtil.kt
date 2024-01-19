package com.example.main.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.application.MApplication

object LocationUtil {

    const val REQUEST_CODE_WIFI = 111
    const val REQUEST_CODE_GPS = 112
    const val REQUEST_CODE_GPS_PERMISSION_SETTING = 113
    const val FOREVER_DENIED = 1
    const val FOREVER_DENIED_NOT = 0
    private var gpsPermissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

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
        //val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
        val panelIntent = Intent(Settings.ACTION_WIFI_SETTINGS)//ACTION_WIFI_IP_SETTINGS
        activity.startActivityForResult(panelIntent, REQUEST_CODE_WIFI)
    }

    /**
     * 打开wifi或进入设置界面
     */
    fun openWifiOrOpenWifiSettingPage(activity: Activity) {

        //android 10以后不能直接打开wifi
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val wifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
            wifiManager?.setWifiEnabled(true)
        } else {
            val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
            activity.startActivityForResult(panelIntent, REQUEST_CODE_WIFI)
        }
    }

    //---------------------- gps -----------------------------
    fun isGpsOpen(context: Context): Boolean {
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * 跳转GPS设置界面
     */
    fun openGpsSettingPage(activity: Activity) {
        if (activity.isFinishing || activity.isDestroyed) {
            return
        }
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity.startActivityForResult(intent, REQUEST_CODE_GPS)
    }

    /**
     * 检查用户是不是永久拒绝了GPS定位权限
     * 注意：当权限为每次使用时询问时，此方法无法返回正确的结果，因为没有申请权限，并通过回调判断
     */
    fun checkGPSPermissionIsForeverDenied(activity: Activity): Boolean{
        //6.0以下不需要动态申请权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false
        }
        if (activity.isFinishing || activity.isDestroyed) return false
        //检查是否已授予gps权限
        var noPermissionArray = checkPermissions(activity, gpsPermissions)
        if (noPermissionArray == null || noPermissionArray.size == 0) {
            //所有权限都已经授予了
            return false
        }
        //如果有权限没有授予，可能存在两种情况：
        //1、没有申请过  2、申请过用户永久拒绝了
        //对于情况1这里不处理，只处理永久拒绝的情况
        return isDenied(activity, noPermissionArray)
    }

    fun checkGPSPermissionIsAllowed(activity: Activity): Boolean{
        //6.0以下不需要动态申请权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (activity.isFinishing || activity.isDestroyed) return false
        //检查是否已授予gps权限
        var noPermissionArray = checkPermissions(activity, gpsPermissions)
        if (noPermissionArray == null || noPermissionArray.size == 0) {
            //所有权限都已经授予了
            return true
        }
        return false
    }

    /**
     * 跳到当前应用的GPS权限设置界面
     */
    fun gotoGPSPermissionSettingPage(activity: Activity) {
        if (activity.isFinishing || activity.isDestroyed) return
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.setData(uri)
        activity.startActivityForResult(intent, REQUEST_CODE_GPS_PERMISSION_SETTING)
    }

    /**
     *  返回没有授权的权限
     */
    fun checkPermissions(context: Context, listPermissions: Array<String>): ArrayList<String>?{
        if (listPermissions.isEmpty()) return null
        val permissions = arrayListOf<String>()
        try {
            for (item in gpsPermissions) {
                if (ContextCompat.checkSelfPermission(context.applicationContext, item) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(item)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return permissions
        }
    }

    private fun isDenied(activity: Activity, listPermissions: ArrayList<String>?): Boolean{
        if (listPermissions == null || listPermissions.size == 0) return false
        try {
            for (item in listPermissions) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, item)) {
                    return true
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun requestGPSPermission(activity: Activity): Boolean{
        //检查是否已授予gps权限
        var noPermissionArray = checkPermissions(activity, gpsPermissions)
        if (noPermissionArray == null || noPermissionArray.size == 0) {
            //所有权限都已经授予了
            return false
        }
        var noPerssions = arrayOfNulls<String>(noPermissionArray.size)
        for ((index, item) in noPermissionArray.withIndex()) {
            noPerssions[index] = item
        }
        ActivityCompat.requestPermissions(activity, noPerssions, REQUEST_CODE_GPS)
        return true
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d("location", "onRequestPermissionsResult requestPermissionCallbackListener =$requestPermissionCallbackListener")
        requestPermissionCallbackListener?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * 检查用户是不是永久拒绝了GPS定位权限
     * 注意：当权限为每次使用时询问时，此方法无法返回正确的结果，因为没有申请权限，并通过回调判断
     */
    fun checkGPSPermissionIsForeverDenied(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Int{
        if (requestCode != REQUEST_CODE_GPS) return -1
        //Logger.d("location", "onRequestPermissionsResult isForeverDenied =$isForeverDenied")

        val noPermissionArray = arrayListOf<String>()
        var isAllGrants = true
        for ((index, item) in grantResults.withIndex()) {
            if (item != PackageManager.PERMISSION_GRANTED) {
                isAllGrants = false
                noPermissionArray.add(permissions.get(index))
            }
        }
        if (isAllGrants) {
            return FOREVER_DENIED_NOT
        }

        //如果有权限没有授予，可能存在两种情况：
        //1、没有申请过  2、申请过用户永久拒绝了
        //对于情况1这里是在申请权限的回调中，只处理永久拒绝的情况
        var isDenied = isDenied(activity, noPermissionArray)
        if (isDenied) {
            return FOREVER_DENIED
        } else {
            return FOREVER_DENIED_NOT
        }
    }


    fun getLocationAsync(listener: MLocationListener) {
        //判断GPS是否打开
        val isGPSOpen = isGPSOpen(MApplication.getContext())
        if (!isGPSOpen) {
            listener.onLocationCallback(false)
            return
        }

        //gps已打开
        val notGrantPermissions = checkPermissions(MApplication.getContext(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
        if (notGrantPermissions == null || notGrantPermissions.size > 0) {
            listener.onLocationCallback(false)
            return
        }
        //权限都已授予

    }

    /**
     * 判断是否可打开了GPS
     */
    fun isGPSOpen(context: Context): Boolean {
        return try {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) { //may be NPE
            false
        }
    }

    interface MLocationListener{
        fun onLocationCallback(isSuccess: Boolean)
    }


    private var requestPermissionCallbackListener: OnRequestPermissionCallbackListener ?= null

    fun setOnRequestPermissionCallbackListener(listener: OnRequestPermissionCallbackListener) {
        this.requestPermissionCallbackListener = listener
    }

    interface OnRequestPermissionCallbackListener{
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    }
}