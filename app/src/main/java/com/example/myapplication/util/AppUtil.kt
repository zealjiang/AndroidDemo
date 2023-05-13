package com.example.myapplication.util

import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.myapplication.bean.CacheListItem

object AppUtil {

    /**
     * 获取非系统应用的信息
     */
    fun getNotSystemAppPackageList(context: Context) : MutableList<CacheListItem>{
        val datas = mutableListOf<CacheListItem>()
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
                datas.add(CacheListItem(packageName,label.toString(),icon,0))
            }
        }
        return datas
    }

}