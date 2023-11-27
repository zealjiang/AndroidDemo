package com.example.main.onepixel;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.application.MApplication;
import com.example.main.ProcessUtil;
import com.example.main.mmkv.MMKVManager;

import java.util.List;

/**
 * 保持后台任务栈
 */

public class KeepTaskOnepxActivity extends Activity {
    protected BroadcastReceiver br;
    public static final String COM_ACTION_TASKTOBACK = "com.action.tasktoback";
    public static final String COM_ACTION_TASKTOBACK_ONEPX_ACT = "com.action.tasktoback.onepxact";
    private boolean isInBack = false;
    private static String appName = getAppName(MApplication.getContext());
    private static String TagPrefix = "onepx "+ appName+" process= "+ ProcessUtil.getCurrentProcessName(MApplication.getContext());
    /** 当前在2秒内重新创建的次数，最大不会大于RE_CREATE_MAX_NUM定义的值 */
    private static int sReCreateNumInMaxSec;
    /** 最大允许在2秒内重新创建的次数，超过这个次数后，在onDestroy中不允许重启 */
    private static final int RE_CREATE_MAX_NUM = 3;
    /** 上一次重新启动的时间 */
    private static long sLastReCreateTime;
    /** 设置连续重新启动的时长，单位毫秒 */
    private static final int RE_CREATE_VALID_TIME_MILLS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mtest",TagPrefix+" onCreate getTaskId()="+getTaskId());
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 3;
        params.y = 3;
        params.height = 400;
        params.width = 400;
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0000")));
        window.setAttributes(params);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("mtest",TagPrefix+" onReceive moveToBack()");
                Log.e("Plugged_locker", "KeepTaskOnepxActivity.this.moveTaskToBack(true)");
                moveToBack();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(COM_ACTION_TASKTOBACK);
        intentFilter.addAction(COM_ACTION_TASKTOBACK_ONEPX_ACT);
        registerReceiver(br, intentFilter);
        MMKVManager.getInstance().setOnePxActivityTaskId(getTaskId());
        moveToBack();
        KeepTaskOnepxHolder.getInstant().setOnePxActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mtest",TagPrefix+" onDestroy");
        try {
            unregisterReceiver(br);
        } catch (IllegalArgumentException e) {
        }
        try {
/*            if(System.currentTimeMillis() - sLastReCreateTime <= RE_CREATE_VALID_TIME_MILLS){
                if(sReCreateNumInMaxSec >= RE_CREATE_MAX_NUM - 1){
                    sReCreateNumInMaxSec = 0;
                    sLastReCreateTime = -1;
                    Log.d("mtest",TagPrefix+" onDestroy 在"+RE_CREATE_VALID_TIME_MILLS+"毫秒内重启次数大于等于最大值"+RE_CREATE_MAX_NUM+"不允许重新启动");
                    return;
                }
            }else{
                sReCreateNumInMaxSec = 0;
                sLastReCreateTime = -1;
            }*/

            startActivity(new Intent(this,KeepTaskOnepxActivity.class));
            Log.e("Plugged_locker", "重新启动" + getTaskId());
            Log.d("mtest",TagPrefix+" onDestroy 重新启动");

            if(sReCreateNumInMaxSec == 0){
                sLastReCreateTime = System.currentTimeMillis();
            }
            if(System.currentTimeMillis() - sLastReCreateTime <= RE_CREATE_VALID_TIME_MILLS){
                sReCreateNumInMaxSec++;
            }else{
                sReCreateNumInMaxSec = 1;
                sLastReCreateTime = System.currentTimeMillis();
            }
        }catch (Exception e){

        }
        Log.e("Plugged_locker", "OnepxActivity onDestroy  getTaskId():" + getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        Log.d("mtest",TagPrefix+" onResume isInBack= "+isInBack);
        isInBack = false;
        Log.e("Plugged_locker", "onResume getTaskId():" + getTaskId());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToBack();
            }
        }, 1000);*/
    }

    private void moveToBack() {
        Log.d("mtest",TagPrefix+" moveToBack isInBack= "+isInBack+ ((isInBack == false) ? " 任务移入后台" : ""));
        if (!isInBack) {
            try {
                KeepTaskOnepxActivity.this.moveTaskToBack(true);
            } catch (Exception e) {
                KeepTaskOnepxActivity.this.finish();
                Log.d("mtest",TagPrefix+" moveToBack error finish= "+e.getMessage());
            }
            isInBack = true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("mtest",TagPrefix+" onKeyDown KEYCODE_BACK");
            moveToBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void checkAndRestart(final Context context) {
        try {
            boolean isHasOnePx = false;
            int onePxTaskid = MMKVManager.getInstance().getOnePxActivityTaskId();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo next : runningTasks) {
                if (next.baseActivity.getPackageName().equalsIgnoreCase(context.getPackageName()) && next.id == onePxTaskid) {
                    isHasOnePx = true;
                }
            }
            Log.d("mtest",TagPrefix+" checkAndRestart  isHasOnePx="+isHasOnePx+ ((isHasOnePx == false) ? "  重新启动" : ""));
            if (!isHasOnePx) {//后台任务栈没有一像素activity，重新启动
                Intent intent = new Intent(context, KeepTaskOnepxActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
