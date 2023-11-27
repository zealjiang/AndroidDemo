package com.example.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.DeadSystemException;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.application.MApplication;
import com.example.main.R;
import com.example.main.annotation.BindView;
import com.example.main.annotation.Butterknife;
import com.example.main.annotation.InjectDIYLayout;
import com.example.main.annotation.Year;
import com.example.main.hook.OnClickHook;
import com.example.main.log.LogManager;
import com.example.main.log.MessageClient;
import com.example.main.util.LibTaskHandler;
import com.example.router.compiler.annotation.MRoute;

@InjectDIYLayout(R.layout.activity_takeout_main)
public class MainActivity extends AppCompatActivity{

    public MainActivity mainActivity;
    MessageClient messageClient;

    @BindView(R.id.tv)
    TextView mBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                Log.e("mtest","e="+e.getMessage());
            }
        });

        //InjectDIYUtils.inject(this);
        setContentView(R.layout.activity_takeout_main);
        mainActivity = this;
        Log.d("mtest","M onCreate");

        Butterknife.bind(this);
        //View mBtn1 = findViewById(R.id.tv);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mtest","-----------click---------");
            }
        });


        try {
            OnClickHook.hookOnClickListener(mBtn1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ProxyUtils.generateClassFile(null,null);
/*        NotificationDemo notificationDemo = new NotificationDemo();
        Log.d("mtest","hook notify");
        NotificationDemo.hookNotificationManager(this);
        Log.d("mtest","send Notify");
        notificationDemo.sendNotify(this);

        try{
            Thread.sleep(1000);
            Log.d("mtest","send no hook Notify");
            notificationDemo.sendNotify(this);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        ProcessUtil.getCurrentProcessName(this);
/*        int uid = ProcessUtil.getUid(this,"com.android.calendar");
        try{
            Class clazz = Class.forName("android.os.ThreadLocalWorkSource");
            Method[] methods = clazz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Log.d("mtest","methods="+methods[i]);
            }
            Method setUidMethod = clazz.getDeclaredMethod("setUid",new Class[]{int.class});
            setUidMethod.invoke(null,uid);
            //ThreadLocalWorkSource.setUid(callingUid);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("mtest","reflect e="+e.getMessage());
        }*/


        //getLifecycle().addObserver(new Watcher01());
        //getLifecycle().addObserver(new MainActivityLifecycleObserver());

/*        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,SecondActivity.class));
                //hasOverlayPermission();

*//*                for (int i = 0; i < 10; i++) {
                    Log.d("mtest","i = "+i);
                }*//*
                LogManager.getInstance().d("mtest",ProcessUtil.getCurrentProcessNameByApplication()+"  i = ");
            }
        });*/


/*        Log.d("mtest","uid="+Binder.getCallingUid());
        int uid = ProcessUtil.getUid(this,"com.android.contacts");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.d("mtest","set uid="+Binder.getCallingUid());
            Binder.setCallingWorkSourceUid(uid);
        }


        long token = Binder.clearCallingIdentity();
        ProcessUtil.openApp(MainActivity.this,"com.android.contacts");
        Binder.restoreCallingIdentity(token);*/

/*        findViewById(R.id.tv).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("mtest","background start secondActivity");
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        },10*1000);*/


        //startActivity(new Intent(MainActivity.this,SecondActivity.class));
        //ThreadLocalWorkSource.setUid(callingUid);

/*        JoinTest joinTest = new JoinTest();
        joinTest.canDo();

        try{
            Class innerclass =Class.forName("com.example.main.JoinTest$InnerClass");
            //Method method = innerclass.getDeclaredMethod("add");
            //method.invoke(innerclass.newInstance(Class.forName("com.example.main.JoinTest").newInstance()));
            //Child child = childClass.getConstructor(new Class[]{motherClassType}).newInstance(new Object[]{mother});


            //Constructor con= innerclass.getConstructor(new Object[]{joinTest});
            //con.setAccessible(true);
            //通过构造器对象 newInstance 方法对对象进行初始化 有参数构造函数
            ///Object obj=con.newInstance();
            //method.invoke(obj);

        }catch (Exception e){
            e.printStackTrace();
            Log.d("mtest","error ="+e.getMessage());
        }*/

/*        if(true) {
            throw new RuntimeException();
        }*/

/*        if(true) {
            throw new NullPointerException();
        }*/
/*
        try {
            e();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mtest","e = "+e.getMessage());
        }
*/


        Log.d("mtest","-1 << 29 = "+(-1 << 29));
        //testfor();


        //注册安装apk监听
/*        AdAppInstallReceiver installReceiver = new AdAppInstallReceiver();
        installReceiver.register(this);*/

        //hasOverlayPermission();
        //notification();
        //setAlarm(0,999);
        //AdApkInstallOrOpenService.startServiceOpen("");

        //LogManager.getInstance().deleteLog();
/*        findViewById(R.id.tv).postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 8000; i++) {
                    Log.d("mtest","MainActivity write log="+i);
                    //LogManager.getInstance().d("mtest",ProcessUtil.getCurrentProcessNameByApplication()+"  i = "+i);
                    LogUtil.getInstance().d("mtest",ProcessUtil.getCurrentProcessNameByApplication()+"  i = "+i);
*//*                    try{
                        Thread.sleep(1,0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }*//*
                }
            }
        },700);*/

        LibTaskHandler.runOnUiThread(()->{
            LogManager.getInstance().d("mtest","test xxx");
            Log.d("mtest","after LogManager.getInstance().d");
        },3000);

    }

    void e() throws DeadObjectException{
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            throw new RuntimeException(new DeadSystemException());
            //throw new DeadSystemException();
            //throw new DeadObjectException();
        }
    }

    void notification(){
        Log.d("mtest","notification");
        try{
            String CHANNEL_ID = "1000";
            int notifyId = 100;

/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String description = "通知描述详情";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "demo 通知", importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = MApplication.getContext().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }*/


            Intent fullScreenIntent = new Intent(MApplication.getContext(), SecondActivity.class);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(MApplication.getContext(), 101,
                    fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(MApplication.getContext(), CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Incoming call")
                            .setContentText("(919) 555-1234")
                            //以下为关键的3行
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_CALL)
                            .setFullScreenIntent(fullScreenPendingIntent, true);

            NotificationManager notifyManager = (NotificationManager) MApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notifyManager.notify(notifyId, notificationBuilder.build());
        }catch (Exception e){
            e.printStackTrace();
            Log.d("mtest","notify error="+e.getMessage());
        }
    }

    private void setAlarm(long delayTimeMills,int requestCode) {
        Log.d("mtest","setAlarm delayTimeMills="+delayTimeMills);
        Context context = MApplication.getContext();

        Intent intent = new Intent(context, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
/*        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + delayTimeMills, pendingIntent);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayTimeMills, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + delayTimeMills, pendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayTimeMills, pendingIntent);
        }
    }

    boolean hasOverlayPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            boolean canDrawOver = Settings.canDrawOverlays(MApplication.getContext());
            Log.d("mtest","hasOverlayPermission  canDrawOver="+canDrawOver);
            if(!canDrawOver){
                //若未授权，申请权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:"+MApplication.getContext().getPackageName()));
                startActivityForResult(intent,100);
            }
            return canDrawOver;
        }else{
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mtest","requestCode="+requestCode+"   resultCode="+resultCode+"  data="+data);
        if(resultCode == RESULT_OK){//-1
            Log.d("mtest","RESULT_OK ");
            if(requestCode == 100){

            }
        }else if(resultCode == RESULT_CANCELED){//0
            Log.d("mtest","RESULT_CANCELED ");
        }
    }

    private void testfor(){
        int i = 0;
        retry:
        for (;;) {
            Log.d("mtest", "testfor outer i = " + i);
            if (i > 6) {
                break;
            }

            int j = 0;
            for (;;) {
                Log.d("mtest", "testfor j = " + j);

                if (j == 1) {
                    Log.d("mtest", "testfor continue retry i = " + i);
                    break retry;
                }

                if (j == 2) {
                    Log.d("mtest", "testfor break retry i = " + i);
                    continue retry;
                }

                if (j > 3) {
                    break;
                }
                j++;
            }
            i++;
        }
        Log.d("mtest","testfor after for");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mtest","M onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("mtest","M onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("mtest","M onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("mtest","M onStop");
/*        TaskUtils.moveTaskToFront(this,true);
        KeepTaskOnepxActivity.checkAndRestart(this);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mtest","M onDestroy");

        messageClient.disconnectService();
    }


    @Year
    public int returnYear() {
         return 666; // warning
        //return Year.LAST;   // ok
    }
}