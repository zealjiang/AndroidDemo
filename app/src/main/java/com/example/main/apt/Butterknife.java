package com.example.main.apt;

import android.app.Activity;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Butterknife {

/*    //反射方式
    public static void bind(Activity activity){
        Class clazz = activity.getClass();
        //拿到Activity中声明的所有成员属性
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            BindView annotation = field.getAnnotation(BindView.class);
            //成员属性是否有@BindView注解
            if(annotation != null){
                try{
                    field.setAccessible(true);
                    //如果有注解，通过反射调用设置属性的值
                    field.set(activity,activity.findViewById(annotation.value()));
                }catch (Exception e){}
            }
        }
    }*/


    //注解方式
    public static void bind(Activity activity){
        try {
            //拼接成注解处理器中生产的类名
            String className = activity.getClass().getCanonicalName()+"Binding";
            Log.d("APT","className ="+className);
            //通过反射创建对象，此时会调用注解处理器生成类的构造方法，在构造方法中调用activity.findviewById(id)
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getDeclaredConstructor(activity.getClass());
            constructor.newInstance(activity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
