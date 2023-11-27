package com.example.network.utils


object MoshiUtils {

    fun <T>toJson(bean: T): String{

        return ""
    }

    fun <T: Any> fromJson(error: String, clazz: Class<T>): T?{
        return null
    }
}