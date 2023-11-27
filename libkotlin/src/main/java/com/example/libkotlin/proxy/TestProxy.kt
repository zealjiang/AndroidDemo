package com.example.libkotlin.proxy

import java.lang.reflect.Proxy

class TestProxy {

    fun test(): Unit{
        val qiruiCar = QiruiCar()
        val invocationHandlerImpl = InvocationHandlerImpl(qiruiCar)
        val proxy = Proxy.newProxyInstance(qiruiCar.javaClass.classLoader, qiruiCar.javaClass.interfaces, invocationHandlerImpl) as ICar
        proxy.brand()
        proxy.engine()

    }

    fun test2(): Unit{
        val qiruiCar = QiruiCar()
        val invocationHandlerImpl = InvocationHandlerImpl(qiruiCar)
        val proxy = MProxy.newProxyInstance(qiruiCar.javaClass.classLoader, qiruiCar.javaClass.interfaces, invocationHandlerImpl) as ICar
        proxy.brand()
        proxy.engine()

    }
}

fun main() {
    val testProxy = TestProxy()
    testProxy.test()
}