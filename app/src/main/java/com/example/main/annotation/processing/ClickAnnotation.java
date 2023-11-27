package com.example.main.annotation.processing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickAnnotation {
    //方法名
    String listenerName();
    //参数类型
    Class listenerType();
    //回调方法
    String callBackMethod();
}
