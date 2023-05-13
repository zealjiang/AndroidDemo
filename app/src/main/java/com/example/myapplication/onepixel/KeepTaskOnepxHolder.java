package com.example.myapplication.onepixel;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class KeepTaskOnepxHolder {
    private static KeepTaskOnepxHolder sInstance;
    private WeakReference<Activity> onePxActivity;

    private KeepTaskOnepxHolder() {
    }


    public static KeepTaskOnepxHolder getInstant() {
        if (sInstance == null) {
            synchronized (KeepTaskOnepxHolder.class) {
                if (sInstance == null) {
                    sInstance = new KeepTaskOnepxHolder();
                }
            }
        }
        return sInstance;

    }

    public Activity getOnePxActivity() {
        if(onePxActivity == null){
            return null;
        }
        return onePxActivity.get();
    }

    public void setOnePxActivity(Activity onePxActivity) {
        this.onePxActivity = new WeakReference<Activity>(onePxActivity);
    }
}
