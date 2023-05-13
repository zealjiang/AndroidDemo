package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;



public class AdApkInstallOrOpenService extends Service {

    public static final String TAG = "mtest";//"AdApkInstallOrOpenService";
    public static final String ACTION = "com.special.popup.adinstall.AdApkInstallOrOpenService.action";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"AdApkInstallOrOpenService onStartCommand");

        AdApkInstallOrOpenReceiver.sendMsg(intent.getExtras().getString("packageName"));
        return START_NOT_STICKY;
    }

    public static void startServiceOpen(String packageName) {
        Log.d(TAG,"startServiceOpen packageName="+packageName);
        openService(packageName,0,"","");
    }


    private static void openService(String packageName,int type,String appPath,String platform) {
        Log.d(TAG,"openService packageName="+packageName+" type="+type+" appPath="+appPath+" platform="+platform);
/*        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            JobSchedulerService.startJobScheduler(MApplication.getContext());
        }else{*/
            try{
                Bundle bundle = new Bundle();
                bundle.putString("packageName",packageName);
                Intent intent = new Intent(AdApkInstallOrOpenService.ACTION);
                intent.putExtras(bundle);
                intent.setPackage(MApplication.getContext().getPackageName());
                MApplication.getContext().startService(intent);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(TAG,"openService fail "+e.getMessage());
            }
/*        }*/
    }

}
