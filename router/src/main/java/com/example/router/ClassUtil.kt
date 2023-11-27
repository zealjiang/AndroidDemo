package com.example.router

import android.content.Context
import android.os.Build
import android.util.Log
import dalvik.system.DexFile

object ClassUtil {
    val TAG = "ClassUtil"
    private fun getSourcePaths(context: Context): List<String> {
        var applicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        var sourcePaths = ArrayList<String>()
        //当前应用的apk文件
        sourcePaths.add(applicationInfo.sourceDir)
        Log.d(TAG, "applicationInfo.sourceDir ="+applicationInfo.sourceDir)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != applicationInfo.splitSourceDirs) {
                sourcePaths.addAll(applicationInfo.splitSourceDirs!!)
                Log.d(TAG, "splitSourceDirs ="+applicationInfo.splitSourceDirs)
            }
        }

        for (item in sourcePaths) {
            Log.d(TAG, "sourcePath =$item")
        }
        return sourcePaths
    }

    fun getFileNameByPackageName(context: Context, packageName: String): java.util.HashSet<String> {
        //拿到apk中的dex地址
        var classNames = HashSet<String>()
        val paths = getSourcePaths(context)

        for (path in paths) {
            var dexFile: DexFile ?= null

            try {
                dexFile = DexFile(path)
                Log.d(TAG, "dexFile =$dexFile")
                var dexEntries =  dexFile.entries()
                Log.d(TAG, "dexEntries =$dexEntries")

                //遍历整个apk中的所有类
                while (dexEntries.hasMoreElements()) {
                    // 包名+类名， 整个apk中所有的类
                    var className = dexEntries.nextElement()
                    //Log.d(TAG, "className =$className")
                    if (className.startsWith(packageName)) {
                        Log.d(TAG, "className =$className")
                        classNames.add(className)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (null != dexFile) {
                    try {
                        dexFile.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        return classNames
    }
}

/**
 * 打印：
 * applicationInfo.sourceDir =/data/app/~~kZg7OL5k-39wyZTzsZDdBw==/com.example.myapplication-IGmlgDJjOoEGt1lXab3vTQ==/base.apk
 * sourcePath =/data/app/~~kZg7OL5k-39wyZTzsZDdBw==/com.example.myapplication-IGmlgDJjOoEGt1lXab3vTQ==/base.apk
 * dexFile =/data/app/~~kZg7OL5k-39wyZTzsZDdBw==/com.example.myapplication-IGmlgDJjOoEGt1lXab3vTQ==/base.apk
 * dexEntries =dalvik.system.DexFile$DFEnum@afcc550
 *
 * className =com.sun.xml.internal.bind.v2.schemagen.xmlschema.Documentation
 * className =com.sun.xml.internal.bind.v2.schemagen.xmlschema.Element
 * ...
 * className =com.sun.org.apache.xml.internal.dtm.ref.sax2dtm
 * className =java.awt.SystemTray
 * ...
 * className =org.omg.PortableInterceptor.Interceptor
 * ...
 * className =sun.invoke.empty.Empty
 * ...
 * className =kotlin.NumbersKt
 * ...
 * className =com.example.routers.Register
 * className =com.example.routers.Router
 * className =com.example.routers.RouterRegister
 */