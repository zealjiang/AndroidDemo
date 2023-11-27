package com.example.main.proxy;

import android.util.Log;

import com.example.application.MApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import sun.misc.ProxyGenerator;

public class ProxyUtils {

    public static void generateClassFile(Class clazz,String proxyName){
        FileOutputStream out = null;
        try {
            Class iNotiMngClz = Class.forName("android.app.INotificationManager");

            getMethods(iNotiMngClz);

            byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{iNotiMngClz});

            Log.e("mtest","generateClassFile classFile="+classFile.length);

            out = new FileOutputStream(MApplication.getContext().getFilesDir()+"/proxy.class");
            //out = new FileOutputStream("/sdcard/INotificationManager" + "$Proxy0.class");
            out.write(classFile);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mtest","generateClassFile e="+e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private static void getMethods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Log.d("mtest","proxy method="+methods[i].getName());
        }
    }
}
