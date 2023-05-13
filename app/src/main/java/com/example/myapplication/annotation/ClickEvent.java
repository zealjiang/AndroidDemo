package com.example.myapplication.annotation;

import android.view.View;
import com.example.myapplication.annotation.processing.ClickAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ClickAnnotation(listenerName = "setOnClickListener", listenerType = View.OnClickListener.class,
        callBackMethod = "onClick")
public @interface ClickEvent {
    int[] value();
}
