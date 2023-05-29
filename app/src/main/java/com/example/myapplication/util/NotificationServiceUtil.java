package com.example.myapplication.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;


public class NotificationServiceUtil {
    private static final String TAG = "NotificationServiceUtil";

    /**
     * @param context
     * @return service is open return null, otherwise return the intent to start
     * settings
     */
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

    public static Intent getNotificationServiceSettingIntent() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 18) {
            intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        } else {
            intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }



    public static boolean checkServiceValid(Context context) {
        final boolean valid = checkServiceStatus(context) == null;
//        OpLog.toFile("AnalyzeNotificationAc", "checkServiceValid -> valid:" + valid);
        return valid;
    }


    public static boolean CheckNotifiServiceValid(Context context) {
        final String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
//        OpLog.toFile("AnalyzeNotificationAc", "CheckNotifiServiceValid -> flat:" + flat);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null && cn.getPackageName().equals(context.getPackageName())) {
//                    OpLog.toFile("AnalyzeNotificationAc", "CheckNotifiServiceValid -> packageName:" + cn.getPackageName());
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean IsSystemSupportNotifyMsg(){
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean IsNotificationServiceEnable(Context context){
        if ( !IsSystemSupportNotifyMsg() )
            return false;

        if (Build.VERSION.SDK_INT >= 18) {
            return CheckNotifiServiceValid(context);
        } else {
            return false;
        }
    }

    public static Intent getNotificationServiceSettingIntent(int requestCode) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 18) {
            intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        } else {
            intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        }
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

}
