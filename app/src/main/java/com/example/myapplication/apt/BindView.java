package com.example.myapplication.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//反射方式需要在运行期通过反射的方式获取view的id值，所以为RetentionPolicy.RUNTIME
@Target(ElementType.FIELD)
public @interface BindView {
    int value();
}
