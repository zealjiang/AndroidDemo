package com.example.main.log;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import java.lang.ref.WeakReference;

public class MessengerService extends Service {
    private static final int REPLY_MSG_ID = 2;
    private static final int MSG_ID = 1;

    public MessengerService(){
        Log.d("mtest","MessengerService create");
    }

    public static boolean isRunService(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    static class BoundServiceHandler extends Handler {
        private final WeakReference<MessengerService> mService;
        public BoundServiceHandler(MessengerService service){
            mService = new WeakReference<>(service);
        }
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_ID:
                    //Messenger replyMessenger = msg.replyTo;
                    String logTag = msg.getData().getString("log_tag");
                    String logMsg = msg.getData().getString("log_msg");
                    Log.d("mtest","MessengerService receive logTag="+logTag+" logMsg="+logMsg);

                    if("delete".equals(logTag)){
                        LogUtil.getInstance().delete();
                    }else{
                        LogUtil.getInstance().d(logTag,logMsg);
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    private final Messenger mMessenger = new Messenger(new BoundServiceHandler(this));
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        IBinder binder = mMessenger.getBinder();
        Log.d("mtest","onBind "+intent+"  binder="+binder);
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        Log.d("mtest"," onUnbind "+intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("mtest"," onRebind "+intent);
        super.onRebind(intent);
    }

    public void serviceAdd(){
        Log.d("mtest"," serviceAdd ");
    }

/*    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("mtest"," onStartCommand ");
        return super.onStartCommand(intent, flags, startId);
    }*/

    @Override
    public void onDestroy() {
        Log.d("mtest","MessengerService onDestroy ");
        stopSelf();
        super.onDestroy();
    }
}
