package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationUtils extends ContextWrapper {

    private static final String TAG = NotificationUtils.class.getSimpleName();
    public static final String id = "channel_1";
    public static final String name = "notification";
    private NotificationManager manager;
    private Context mContext;

    public NotificationUtils(Context base) {
        super(base);
        mContext = base;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
         if (manager == null) {
                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
             }
        return manager;
    }

    /**
     * 发送通知
     * @param title
     * @param content
     * @param type
     */
    public void sendNotificationFullScreen(String title, String content, String type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification notification = getChannelNotificationQ(title, content, type);
            getManager().notify(1, notification);
        }
    }

    public void clearAllNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public Notification getChannelNotificationQ(String title, String content, String type) {
        Log.e(TAG, "getChannelNotificationQ: " );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setTicker(content)
                .setContentText(content)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis() + 1000)
                .setCategory(Notification.CATEGORY_CALL)
                .setFullScreenIntent(getIntent(), true);

        Notification incomingCallNotification = notificationBuilder.build();
        return incomingCallNotification;
    }

    public void startActivityByAlarm() {
        if (Build.VERSION.SDK_INT < 29) {
            return;
        }

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return ;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //clearAllNotification();
            alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + 100, getIntent());
        }
    }

    private PendingIntent getIntent() {
        Intent fullScreenIntent = new Intent(this, SecondActivity.class);
        fullScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fullScreenIntent.putExtra("action", "callfromdevice");
        fullScreenIntent.putExtra("type", "type");
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return fullScreenPendingIntent;
    }
}
