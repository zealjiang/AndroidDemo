package com.example.myapplication.onepixel;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.example.myapplication.MApplication;
import com.example.myapplication.mmkv.MMKVManager;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {

    private static String appName = getAppName(MApplication.getContext());
    private static String TagPrefix = "TaskUtils "+ appName;

    /**
     * @param isOnlyOnePx 是否只使用一像素activity
     */
    public static void moveTaskToFront(Context context, boolean isOnlyOnePx) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int taskid = MMKVManager.getInstance().getOnePxActivityTaskId2();
            Log.d("mtest",TagPrefix+" moveTaskToFront  isOnlyOnePx= "+isOnlyOnePx + "  taskid="+taskid);
            if (!isOnlyOnePx) {
                List<Integer> tempTaskIdList = new ArrayList<>();
                List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
                for (ActivityManager.RunningTaskInfo next : runningTasks) {
                    if (next.baseActivity.getPackageName().equalsIgnoreCase(context.getPackageName())) {
                        tempTaskIdList.add(next.id);
                    }
                }

                if (!tempTaskIdList.contains(taskid) && tempTaskIdList.size() > 0) {//优先取用一像素activity
                    Log.e("Plugged_locker", "moveTaskToFront 使用的不是一像素activity，可能拉起主界面");
                    taskid = tempTaskIdList.get(0);
                    delayStartOnePx(context);
                }
            }
            if (taskid != 0) {
                activityManager.moveTaskToFront(taskid, 0);
                activityManager.moveTaskToFront(taskid, 0);
                activityManager.moveTaskToFront(taskid, 0);
                activityManager.moveTaskToFront(taskid, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void delayStartOnePx(final Context context) {
        Log.d("mtest",TagPrefix+" delayStartOnePx  500");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, KeepTaskOnepxActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }, 500);

    }

    public static String getAppName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            String appName = String.valueOf(packageManager.getApplicationLabel(context.getApplicationInfo()));
            //Log.d("mtest","getAppName ="+appName);
            return appName;
        } catch (Throwable e) {
        }
        return context.getPackageName();
    }
}
