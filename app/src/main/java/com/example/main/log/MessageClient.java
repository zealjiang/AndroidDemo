package com.example.main.log;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.main.ProcessUtil;
import com.example.main.ProcessUtil;

import java.util.concurrent.TimeUnit;

public class MessageClient {
    private static final int MSG_ID = 1;
    private static final int REPLY_MSG_ID = 2;
    private static final String DELETE_CMD = "delete";
    private boolean mServiceConnected = false;
    //用于向Service端发送消息的Messenger
    private Messenger mBoundServiceMessenger = null;

    /** 此context 需为 Activity 或 Service **/
    private Context mContext;
    protected MessageClient(Context context){
        mContext = context;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("mtest","onServiceDisconnected "+mContext+"  name="+name);
            mBoundServiceMessenger = null;
            mServiceConnected = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundServiceMessenger = new Messenger(service);
            mServiceConnected = true;
            Log.d("mtest","onServiceConnected "+mContext+"  service="+service);
        }
    };

    public void bindService(){
        if(mContext == null){
            Log.e("mtest","bindService fail mClientHost is null");
            return;
        }
        String processName = ProcessUtil.getCurrentProcessName(mContext);
        Log.d("mtest","bindService mClientHost="+mContext+" process="+ processName);
        if(!processName.contains(":custom_process")) {
            mContext.bindService(new Intent(mContext, MessengerService.class), conn, Context.BIND_AUTO_CREATE);
        }
    }

    public void unBindService(){
        if(mContext == null){
            Log.e("mtest","unBindService fail mClientHost is null");
            return;
        }
        Log.d("mtest","unBindService mClientHost="+mContext+"  serviceConnection="+conn);
        try {
            mContext.unbindService(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendMsgToServier(String tag,String sMsg){
        if(mServiceConnected){
            //获取消息对象
            Message msg = Message.obtain(null, MSG_ID, 0, 0);
            try{
                Log.d("mtest","sendMsgToServier  sMsg="+sMsg);
                Bundle bundle = new Bundle();
                if(DELETE_CMD.equals(tag)){
                    bundle.putString("log_tag",tag);
                }else{
                    bundle.putString("log_tag",tag);
                    bundle.putString("log_msg",sMsg);
                }

                //replyTo参数包含客户端Messenger
                //msg.replyTo = mReceiveMessenger;
                msg.setData(bundle);
                //向Service端发送消息
                mBoundServiceMessenger.send(msg);
                Log.d("mtest","sendMsgToServier  after send sleep "+sMsg);
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("mtest","sendMsgToServier  after sleep "+sMsg);
            }catch(RemoteException re){
                re.printStackTrace();
                Log.e("mtest","sendMsgToServier error="+re.getMessage());
            }
        }else{
            Log.w("mtest","sendMsgToServier fail mServiceConnected is false");
        }
    }

    public void log(String tag,String msg){
        sendMsgToServier(tag,msg);
    }

    public void deleteLog(){
        sendMsgToServier(DELETE_CMD,null);
    }

    public void disconnectService(){
        if(mContext != null && mServiceConnected){
            mContext.unbindService(conn);
            mServiceConnected = false;
        }
    }
}
