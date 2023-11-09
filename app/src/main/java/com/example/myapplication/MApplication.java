package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.log.LogManager;
import com.example.myapplication.util.SystemUtil;
import com.example.router.SimpleRouter;
import com.tencent.mmkv.MMKV;

public class MApplication extends Application implements LifecycleObserver {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        Log.d("mtest","MApplication onCreate--- process="+ProcessUtil.getCurrentProcessName(this));

        LogManager.init(this);
        initMMKV();
        //KeepTaskOnepxActivity.checkAndRestart(this);
        //KeepTaskOnepxActivity2.checkAndRestart(this);


        //arouter
        if (true) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application


        //----router----
        //RouterRegister.INSTANCE.init();
        SimpleRouter.INSTANCE.init(this);
    }



    private void checkADB(){
        boolean isADBEnable = SystemUtil.isADBEnable();
        boolean isEnableAdb = SystemUtil.isEnableAdb(this);
        boolean isOpenDevelopmentSetting = SystemUtil.isOpenDevelopmentSetting(this);
        Log.d("mtest", "isADBEnable ="+isADBEnable+"  isEnableAdb ="+isEnableAdb+"  isOpenDevelopmentSetting ="+isOpenDevelopmentSetting);
        Toast.makeText(this,"isADBEnable ="+isADBEnable+"  isEnableAdb ="+isEnableAdb
                +"  isOpenDevelopmentSetting ="+isOpenDevelopmentSetting,Toast.LENGTH_LONG).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onForeground() {
        Log.d("mtest", "应用回到前台");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onBackground() {
        Log.d("mtest", "应用退到后台");
    }

    public static Context getContext(){
        return mContext;
    }


    private void initMMKV() {
        String mmkvRootDir = mContext.getFilesDir().getAbsolutePath() + "/mmkv";
        MMKV.initialize(mmkvRootDir);
    }
}
