package com.example.libkotlin.proxy

import android.app.Service
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class InvocationHandlerImpl(private val realObject: ICar) : InvocationHandler{

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        println("--- proxy: ${proxy?.javaClass?.name}")
        println("--- method: ${method?.name}")
        println("---proxy method---args =${args?.size}")
        //return method?.invoke(proxy, *(args ?: emptyArray())) //!千万不能这么调用，不然成死循环了，当前这个invoke()就是由proxy调起的，如果这里我们反射再传入proxy，不就成又使用proxy调用这个方法了吗


        /**
         *     因为invoke的第二个参数是可变参数，在传入数组时，要在数据前面加*号
         *     public native Object invoke(Object obj, Object... args)
         *             throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
         */
        return method?.invoke(realObject, *(args ?: emptyArray()))
    }
}