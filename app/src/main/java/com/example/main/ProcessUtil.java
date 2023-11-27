package com.example.main;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.List;

public class ProcessUtil {


    private static String currentProcessName;

    /**
     * @return 当前进程名
     */
    @Nullable
    public static String getCurrentProcessName(@NonNull Context context) {
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        //1)通过Application的API获取当前进程名
        currentProcessName = getCurrentProcessNameByApplication();
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        //2)通过反射ActivityThread获取当前进程名
        currentProcessName = getCurrentProcessNameByActivityThread();
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        //3)通过ActivityManager获取当前进程名
        currentProcessName = getCurrentProcessNameByActivityManager(context);

        return currentProcessName;
    }


    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private static String getCurrentProcessNameByApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private static String getCurrentProcessNameByActivityThread() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private static String getCurrentProcessNameByActivityManager(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    private static void getAppProcessName(Context context) {
        //当前应用pid
        final PackageManager packageManager = context.getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // get all apps
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        for (int i = 0; i <apps.size() ; i++) {
            String name = apps.get(i).activityInfo.packageName;
/*            if(name.contains("huawei")&&name.contains("android")){
                Log.i("mtest", "getAppProcessName: "+apps.get(i).activityInfo.packageName);
            }*/
            Log.d("mtest", "getAppProcessName: "+apps.get(i).activityInfo.packageName);
        }
    }


    public static int getUid(Context context,String packageName){
        try {
            //String packageName = "com.android.calendar"; // 指定包名
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);
            Log.d("UID", "getUid:" + ai.uid);
            Log.d("mtest", "packageName="+packageName+" uid= "+ai.uid);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void openApp(Context context,String mPackageName) {
        //打开应用
        //已安装, 打开
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(mPackageName);
            if(intent == null){
                Log.e("mtest","openApp error intent null mPackageName = "+mPackageName);
                return;
            }
            //intent.putExtra("from", new Random().nextInt(12536));
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



