package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;


/**
 * 监听应用安装的广播接收器
 */
public class AdAppInstallReceiver extends BroadcastReceiver {


    public void register(Context context){
        IntentFilter intentFilter  = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addDataScheme("package");
        context.registerReceiver(this,intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent) {
            return;
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            Uri data = intent.getData();
            if (data == null) {
                return;
            }

            final String packageName = data.getSchemeSpecificPart();
            if (TextUtils.isEmpty(packageName)) {
                return;
            }

            //AdApkInstallOrOpenService.startServiceOpen(packageName);
            AdApkInstallOrOpenReceiver.sendMsg(packageName);
        }
    }
}