package com.example.main.annotation;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.example.main.annotation.processing.ClickAnnotation;
import com.example.main.annotation.processing.ClickAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ButterknifeClick {
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

    public static void onClick(Activity activity){
        Class clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods) {
            ClickEvent annotation = method.getAnnotation(ClickEvent.class);
            //成员属性是否有@BindView注解
            if (annotation != null) {
                try {
                    //如果有注解，通过反射调用设置属性的值
                    int[] viewIds = annotation.value();
                    for (int i = 0; i < viewIds.length; i++) {
                        Log.d("onClick","viewId = "+viewIds[i]);
                        View view = activity.findViewById(viewIds[i]);
                        Log.d("onClick","view = "+view);

/*                        //方法一：通过直接给view设置setOnClickListener
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("onClick","proxy click view = "+view);
                                try {
                                    method.invoke(activity,v);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });*/

                        //方法二：通过使用动态代理，给view设置生成的动态代理对象
                        Object obj = Proxy.newProxyInstance(view.getClass().getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method_, Object[] args) throws Throwable {
                                Log.d("onClick","method  = "+method_.getName()+" "+method_.toString());
                                method.invoke(activity,view);
                                return null;
                            }
                        });
                        view.setOnClickListener((View.OnClickListener)obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("onClick","e = "+e.getMessage());
                }
            }
        }
    }


    public static void onClick2(Activity activity){
        Class clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods) {
            Annotation[] annotations = method.getAnnotations();
            Log.d("onClick", "method =" + method.getName());
            for (int i = 0; i < annotations.length; i++) {
                //获取注解对象的class
                Class<? extends Annotation> annotationType = annotations[i].annotationType();
                Log.d("onClick", " annotationType =" + annotationType);
                //获取InjectEvent注解对象
                ClickAnnotation clickAnnotation = annotationType.getAnnotation(ClickAnnotation.class);
                if (clickAnnotation != null) {
                    //说明是ClickEvent 或 LongClickEvent,因为这两注解上加了ClickAnnotation这个注解
                    //获取参数
                    String listenerSetting = clickAnnotation.listenerName();
                    Class listenerType = clickAnnotation.listenerType();
                    String callBackMethod = clickAnnotation.callBackMethod();
                    Log.d("onClick", "  listenerSetting =" + listenerSetting + "   listenerType =" + listenerType + "  callBackMethod =" + callBackMethod);

                    try {
                        //获取注解里value 即注解的({R.id.text_view1,R.id.text_view2})View的Id值
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] ids = (int[]) valueMethod.invoke(annotations[i]);
                        for (int id : ids) {
                            Log.d("onClick", "id  = " + id);
                            Method methodFindViewById = clazz.getMethod("findViewById", int.class);//activity.findViewById(int id)这个方法是Activity类的
                            View view = (View) methodFindViewById.invoke(activity, id);//findViewById()这个方法是public所以没有设置methodFindViewById.setAccessible(true);

                            //方案一：使用动态代理方式执行点击或长按事件
                            //获得setOnClickListener或setOnLongClickListener的method对象
                            Method setterMethod = view.getClass().getMethod(listenerSetting, listenerType);//listenerSetting方法名，listenerType方法的参数
                            //使用动态代理的方式拦截setOnClickListener或setOnLongClickListener方法的执行
                            Object obj = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, new InvocationHandler() {
                                @Override
                                public Object invoke(Object proxy, Method method_, Object[] args) throws Throwable {
                                    Log.d("onClick", "method  = " + method_.getName() + " " + method_.toString());
                                    return method.invoke(activity, view);
                                }
                            });
                            setterMethod.invoke(view, obj);

/*                            //方案二：使用动态代理方式执行点击或长按事件
                            if("setOnClickListener".equals(listenerSetting)){
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            method.invoke(activity, view);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }else if("setOnLongClickListener".equals(listenerSetting)){
                                view.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        try {
                                            method.invoke(activity, view);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        return true;
                                    }
                                });
                            }*/

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
