package com.example.main.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.util.Log
import com.example.main.bean.CacheListItem


object AppUtil {

    /**
     * 获取非系统应用的信息
     */
    fun getNotSystemAppPackageList(context: Context) : MutableList<com.example.main.bean.CacheListItem>{
        val datas = mutableListOf<com.example.main.bean.CacheListItem>()
        val packages = context.packageManager.getInstalledPackages(0)
        val packageNameList = arrayListOf<String>()
        for (packageInfo in packages) {
            // 判断系统/非系统应用
            if (ApplicationInfo.FLAG_SYSTEM and packageInfo.applicationInfo.flags != 0) {
                // 系统应用
//                LogUtil.d("getAppList, system packageInfo=" + packageInfo.packageName)
            } else if (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP and packageInfo.applicationInfo.flags != 0) {

            }else {
                // 非系统应用
//                LogUtil.d("getAppList, not system packageInfo=" + packageInfo.packageName)
                packageNameList.add(packageInfo.packageName ?: "")
                val packageName = packageInfo.packageName
                val applicationInfo = packageInfo.applicationInfo
                val label = applicationInfo.loadLabel(context.packageManager)
                val icon = applicationInfo.loadIcon(context.packageManager)
                datas.add(
                    com.example.main.bean.CacheListItem(
                        packageName,
                        label.toString(),
                        icon,
                        0
                    )
                )
            }
        }
        return datas
    }

    /**
     * 获取所有安装应用的信息
     */
    fun getAppPackageList(context: Context) : MutableList<com.example.main.bean.CacheListItem>{
        val datas = mutableListOf<com.example.main.bean.CacheListItem>()
        val packages = context.packageManager.getInstalledPackages(0)
        val packageNameList = arrayListOf<String>()
        for (packageInfo in packages) {
            // 判断系统/非系统应用
            if (ApplicationInfo.FLAG_SYSTEM and packageInfo.applicationInfo.flags != 0) {
                // 系统应用
                packageNameList.add(packageInfo.packageName ?: "")
                val packageName = packageInfo.packageName
                val applicationInfo = packageInfo.applicationInfo
                val label = applicationInfo.loadLabel(context.packageManager)
                val icon = applicationInfo.loadIcon(context.packageManager)
                datas.add(
                    com.example.main.bean.CacheListItem(
                        packageName,
                        label.toString(),
                        icon,
                        0
                    )
                )


                Log.d("AppUtil","packageName =$packageName, applicationInfo =$applicationInfo")
            } else if (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP and packageInfo.applicationInfo.flags != 0) {

            }else {
                // 非系统应用
//                LogUtil.d("getAppList, not system packageInfo=" + packageInfo.packageName)
                packageNameList.add(packageInfo.packageName ?: "")
                val packageName = packageInfo.packageName
                val applicationInfo = packageInfo.applicationInfo
                val label = applicationInfo.loadLabel(context.packageManager)
                val icon = applicationInfo.loadIcon(context.packageManager)
                datas.add(
                    com.example.main.bean.CacheListItem(
                        packageName,
                        label.toString(),
                        icon,
                        0
                    )
                )

                Log.d("AppUtil","packageName =$packageName, applicationInfo =$applicationInfo")
            }
        }
        return datas
    }

}