package com.example.myapplication.hook;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.MApplication;
import com.example.myapplication.R;
import com.example.myapplication.SecondActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

public class NotificationDemo {

    private final static String TAG = "mtest";
    public void sendNotify(Context context){
        String CHANNEL_ID = "CM_TEST_1";
        String CHANNEL_NAME = "CM_TEST";
        int notifyId = 100;

        Intent hangIntent = new Intent(context, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1001, hangIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("这是测试通知标题")  //设置标题
                .setContentText("这是测试通知内容") //设置内容
                .setWhen(System.currentTimeMillis())  //设置时间
                .setContentIntent(pendingIntent)
                .setTicker("通知来了")
                .setSmallIcon(R.mipmap.ic_launcher)  //设置小图标  只能使用alpha图层的图片进行设置
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(notifyId, notification);
    }


    public static void hookNotificationManager(final Context context) {

        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Method getService = NotificationManager.class.getDeclaredMethod("getService");
            getService.setAccessible(true);
            // 第一步：得到系统的 sService
            final Object sOriginService = getService.invoke(notificationManager);

            Class iNotiMngClz = Class.forName("android.app.INotificationManager");
            Log.d("mtest","INotificationManager="+iNotiMngClz.getName()+"  "+iNotiMngClz.getPackage());
            getMethods(iNotiMngClz);

            // 第二步：得到我们的动态代理对象
            Object proxyNotiMng = Proxy.newProxyInstance(context.getClass().getClassLoader(), new
                    Class[]{iNotiMngClz}, new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("createNotificationChannels".equals(method.getName())) {
                        //Log.d(TAG, "invoke(). method:" + method);
                        String name = method.getName();
                        Log.d(TAG, "invoke: name=" + name);
/*                        if (args != null && args.length > 0) {
                            for (Object arg : args) {
                                Log.d(TAG, "invoke: arg=" + arg);
                            }
                        }*/
                        Toast.makeText(context.getApplicationContext(), "检测到有人发通知了", Toast.LENGTH_SHORT).show();
                        // 操作交由 sOriginService 处理，不拦截通知
                        return method.invoke(sOriginService, args);
                        // 拦截通知，什么也不做
                        //                    return null;
                        // 或者是根据通知的 Tag 和 ID 进行筛选
                    }
                    return method.invoke(sOriginService, args);
                }
            });

            // 第三步：偷梁换柱，使用 proxyNotiMng 替换系统的 sService
            Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyNotiMng);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mtest","e="+e.getMessage());
        }
    }

    private static void getMethods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Log.d("mtest","proxy declaredMethods="+methods[i].getName());
        }
    }

    public static void generateClassFile(Object obj){
        FileOutputStream out = null;
        try {

            out = new FileOutputStream(MApplication.getContext().getFilesDir()+"/proxy2.class");

            ObjectOutputStream objOut=new ObjectOutputStream(out);

            objOut.writeObject(obj);

            objOut.flush();

            objOut.close();

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
}
