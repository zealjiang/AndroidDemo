package com.example.myapplication.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import java.lang.reflect.Method;

public class SystemUtil {

    /**
     * 检测是否开启动了usb 调试模式
     * @return
     */
    public static boolean isADBEnable(){
        String adb_enable=getSystemProperty("persist.sys.usb.config","") ;
        Log.d("mtest","adb_enable ="+adb_enable);
        if(adb_enable.contains("adb")) {
            return true;
        }
        return false;
    }

    private static String getSystemProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> clazz= Class.forName("android.os.SystemProperties");
            Method get = clazz.getMethod("get", String.class, String.class);
            value = (String)(get.invoke(clazz, key, ""));
        } catch (Exception e) {

        }
        return value;
    }

    /**
     *  检测是否开启动了adb 调试返回值也是true
     * @param context
     * @return
     */
    public static boolean isEnableAdb(Context context){
        boolean enableAdb = (Settings.Secure.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) > 0);
        if(enableAdb){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 开发者选项是否开启
     *
     * @return true 开启
     */
    public static boolean isOpenDevelopmentSetting(Context context) {
        boolean enableAdb = Settings.Secure.getInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        return enableAdb;
    }


    public static Intent checkServiceStatus(Context context) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 18) {
            if (!NotificationServiceUtil.CheckNotifiServiceValid(context)) {
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
        }

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        return intent;
    }
}
