package com.example.main.annotation;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InjectDIYUtils {
    private static final String TAG = "mtest";

    public static void inject(Object context){
        injectDIYLayout (context);
    }

    private static void injectDIYLayout (Object context) {
        Class<?> aClass = context.getClass();
        InjectDIYLayout annotation = aClass.getAnnotation(InjectDIYLayout.class);
        Log.e(TAG, "injectLayout: annotation.value()="+annotation.value());
        try {
            Method contentView = aClass.getMethod("setContentView", int.class);

            try {

                Object invoke = contentView.invoke(context, annotation.value());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
