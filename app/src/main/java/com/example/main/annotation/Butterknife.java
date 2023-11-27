package com.example.main.annotation;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Butterknife {
    public static void bind(Activity activity) {
        Class clazz = activity.getClass();
        //拿到Activity中声明的所有成员属性
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field: declaredFields) {
            BindView annotation = field.getAnnotation(BindView.class);
            //成员属性是否有@BindView注解
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    //如果有注解，通过反射调用设置属性的值
                    field.set(activity, activity.findViewById(annotation.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
