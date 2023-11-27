package com.example.main.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {
    private Object mRealObject;

    public InvocationHandlerImpl(Object realObject) {
        mRealObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("修改方法="+method.getName()+" 可执行自己的代码");
        System.out.println("proxy invoke, proxy = " + proxy.getClass() + ", realObject = " + mRealObject.getClass());
        Object result = method.invoke(mRealObject, args);
        return result;
    }
}
