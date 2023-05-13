package com.example.myapplication.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class LibTaskHandler {
    private static Handler mHandler;

    private LibTaskHandler(){
        Log.d("mtest","---LibTaskHandler---");
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static class SingletonHolder {
        private static LibTaskHandler instance = new LibTaskHandler();
    }

    public static final void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            SingletonHolder.instance.mHandler.post(action);
        } else {
            action.run();
        }
    }

    public static final void runOnUiThread(Runnable action,long delayMillis) {
        SingletonHolder.instance.mHandler.postDelayed(action,delayMillis);
    }
}
