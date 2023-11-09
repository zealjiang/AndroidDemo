package com.example.router

import android.app.Activity
import android.app.Application
import android.util.Log

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
        try {
            val classNames: Set<String> =
                ClassUtil.getFileNameByPackageName(application, "com.example.routers")
            for (className in classNames) {
                val cls = Class.forName(className)

                //cls 是否继承自IRouteLoad
                if (IRouteLoad::class.java.isAssignableFrom(cls)) {
                    Log.d(ClassUtil.TAG, "cls =$cls")
                    val load = cls.newInstance() as IRouteLoad
                    load.loadInto(routes)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 打印：
     * cls =class com.example.routers.Router
     * cls =class com.example.routers.Register
     */
}