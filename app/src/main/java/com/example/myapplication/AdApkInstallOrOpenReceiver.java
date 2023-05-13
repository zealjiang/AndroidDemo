package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.log.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class AdApkInstallOrOpenReceiver extends BroadcastReceiver {

    public static final String TAG = "mtest";
    public AdApkInstallOrOpenReceiver() {
    }

    public static Handler mHandler = new Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent) {
            return;
        }
        if (intent.getAction().equals("action.AdApkInstallOrOpenReceiver")) {
            String packageName = intent.getStringExtra("packageName");
            String type = intent.getStringExtra("type");
            Log.d(TAG, "sendMsg onReceive  packageName=" + packageName + "  type=" + type + "  processId =" + Process.myPid() + "  uid=" + Process.myUid() + "  tid=" + Process.myTid());

/*            boolean boo = hasOverlayPermission(context);
            if(boo){
                Intent intentStart = new Intent(MApplication.getContext(),SecondActivity.class);
                intentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentStart);
            }*/


            Intent mintent = new Intent(context, SecondActivity.class);
            mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            int requestCode = 10000+new Random().nextInt(300);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, requestCode,
                    mintent, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                fullScreenPendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
                Log.d("mtest","e send"+e.getMessage());
                try {
                    context.startActivity(mintent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.d("mtest","ex send"+ex.getMessage());
                }
            }
            notification(context,fullScreenPendingIntent,requestCode);
            //setAlarm(context,100,fullScreenPendingIntent);
        }
    }

    private RemoteViews createRemoteView(Context context, PendingIntent pendingIntent) {
        //创建一个RemoteView实例
        //RemoteViews mRemoteViews = new RemoteViews(MApplication.getContext().getPackageName(), R.layout.test_notifycation);
        RemoteViews mRemoteViews = new RemoteViews(MApplication.getContext().getPackageName(), R.layout.test_notifycation);
        mRemoteViews.setTextViewText(R.id.music_name, "大风吹");
        mRemoteViews.setTextViewText(R.id.singer_name, "阿芳");


        //设置play按钮的点击事件
        mRemoteViews.setOnClickPendingIntent(R.id.btn_play, pendingIntent);

        return mRemoteViews;
    }

    void notification(final Context context,PendingIntent pendingIntent,int notifyId){
        Log.d("mtest","notification");
        try{
            String CHANNEL_ID = "1003_channel";
            String tag = "NNO";

            //context = MApplication.getContext();

            NotificationManager notifyManager = (NotificationManager) MApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notifyManager.cancel(tag,notifyId);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notifyManager.deleteNotificationChannel(CHANNEL_ID);

                String description = "通知描述详情";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "demo 通知", importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notifyManager.createNotificationChannel(channel);
            }

            RemoteViews remoteViews = createRemoteView(context,pendingIntent);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(MApplication.getContext(), CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Incoming call")
                            .setContentText("(919) 555-1234")
                            .setContent(remoteViews)
                            //以下为关键的3行
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setCategory(NotificationCompat.CATEGORY_CALL)
                            .setFullScreenIntent(pendingIntent, true)
                            .setWhen(System.currentTimeMillis() + 1000)
                            //.setContentIntent(fullScreenPendingIntent)
                            .setAutoCancel(true);//在用户点按通知后自动移除通知

            Notification notifycation = notificationBuilder.build();
            notifyManager.notify(tag,notifyId, notifycation);


            Class clazz = Class.forName("android.widget.RemoteViews");
            Field field = clazz.getDeclaredField("mActions");
            field.setAccessible(true);
            ArrayList<Object> objects = (ArrayList) field.get(remoteViews);
            for (int i = 0; i < objects.size(); i++) {
                Log.d("mtest","i ="+i+" action="+objects.get(i));
            }

            //getMethod(view,methodName,param).invoke(view,param);

        }catch (Exception e){
            e.printStackTrace();
            Log.d("mtest","notify error="+e.getMessage());
        }
    }

    private void setAlarm(Context context,long delayTimeMills,PendingIntent pendingIntent) {
        Intent mintent = new Intent(context, SecondActivity.class);
        mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int requestCode = 10000+new Random().nextInt(300);
        pendingIntent = PendingIntent.getActivity(context, requestCode,
                mintent, PendingIntent.FLAG_UPDATE_CURRENT);


        Log.d(TAG,"setAlarm delayTimeMills="+delayTimeMills);
        //Context context = MApplication.getContext();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return ;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + delayTimeMills, pendingIntent);
        }

/*        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
*//*        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + delayTimeMills, pendingIntent);*//*

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayTimeMills, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + delayTimeMills, pendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayTimeMills, pendingIntent);
        }*/
    }


    public static void sendMsg(String packageName) {
        Log.d(TAG, "sendMsg packageName=" + packageName + "  processId =" + Process.myPid() + "  uid=" + Process.myUid() + "  tid=" + Process.myTid());
        Intent intent = new Intent();
        intent.setPackage(MApplication.getContext().getPackageName());
        intent.setAction("action.AdApkInstallOrOpenReceiver");
        intent.putExtra("type", "install");
        intent.putExtra("packageName", packageName);
        MApplication.getContext().sendBroadcast(intent);
    }

    boolean hasOverlayPermission(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            boolean canDrawOver = Settings.canDrawOverlays(MApplication.getContext());
            Log.d("mtest","hasOverlayPermission  canDrawOver="+canDrawOver);
            if(!canDrawOver){
                //若未授权，申请权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:"+MApplication.getContext().getPackageName()));
                context.startActivity(intent);
            }
            return canDrawOver;
        }else{
            return true;
        }
    }

    //=======================================================


}
