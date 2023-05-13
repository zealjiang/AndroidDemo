package com.example.myapplication.log;

import android.content.Context;
import android.util.Log;

public class LogManager {

    private static MessageClient messageClient;

    private LogManager(){}

    private static class SingletonHolder{
        private static LogManager singleton = new LogManager();

    }

    public static void init(Context context){
        if(messageClient != null){
            Log.d("mtest","has init");
            return;
        }
        messageClient = new MessageClient(context);
        messageClient.bindService();
    }

    public static LogManager getInstance(){
        return LogManager.SingletonHolder.singleton;
    }

    public void d(String tag, String msg){
        messageClient.log(tag,msg);
    }

    public void deleteLog(){
        messageClient.deleteLog();
    }

    public void unbind(){
        messageClient.unBindService();
    }
}
