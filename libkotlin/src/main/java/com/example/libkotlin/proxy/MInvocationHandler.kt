package com.example.libkotlin.proxy

import java.lang.reflect.Method

public interface MInvocationHandler {
    @Throws(Throwable::class)
    operator fun invoke(proxy: Any?, method: Method?, args: Array<Any?>?): Any?
}
