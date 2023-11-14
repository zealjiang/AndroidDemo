package com.example.router

import android.app.Activity
import android.app.Application
import android.util.Log
import java.util.ServiceLoader

object SimpleRouter {
    val TAG = "SimpleRouter"
    //private val routes = HashMap<String,Class<*>>()
    private var routes: HashMap<String, Class<out Activity?>> = HashMap() //Class<out Activity?>

    fun putRoute(path: String, clazzName: String) {
        routes.put(path, Class.forName(clazzName) as Class<out Activity?>)
    }

    fun navigation(path: String): Class<*>? {
        return routes[path]
    }

    fun init(application: Application) {
        Log.d(TAG, "init")
        spiLoad()
    }

    private fun spiLoad() {
        Log.d(TAG, "spiLoad")
        var serviceLoader = ServiceLoader.load(IRouteLoad::class.java)
        Log.d(TAG, "spiLoad serviceLoader =$serviceLoader")
        Log.d(TAG, "spiLoad count =${serviceLoader.count()}, count =${serviceLoader.count()}")
        serviceLoader.forEach{
            Log.d(TAG, "spiLoad iRouteLoad =${it}")
            it.loadInto(routes)
        }
    }

    /**
     * 通过遍历apks中所有的类文件，找出我们的路由注册类，完成注册
     * 这种方式因为会遍历apk中所有的类文件，所以效率不高
     */
    private fun traverseApks(application: Application) {
        try {
            //ClassUtil.getFileNameByPackageName会遍历apk中所有的类，包含系统的类，所以如果引的类越多，耗时越长
            val classNames: Set<String> =
                ClassUtil.getFileNameByPackageName(application, "com.example.routers")
            for (className in classNames) {
                val cls = Class.forName(className)
                var isChild = IRouteLoad::class.java.isAssignableFrom(cls)
                Log.d(TAG, "className =$className, isChild =$isChild")
                //cls 是否继承自IRouteLoad
                if (IRouteLoad::class.java.isAssignableFrom(cls)) {
                    Log.d(TAG, "cls =$cls")
                    val load = cls.newInstance() as IRouteLoad
                    load.loadInto(routes)
                }
            }

            //打印routes
            val iterator = routes.iterator()
            for (path in routes.keys) {
                Log.d(TAG, "path =$path, class =${routes.get(path)}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}