package com.example.main.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.example.application.MApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class LogUtil {


    private  String processName;
    private  int actionNum;

    private  SimpleDateFormat sdf;

    private  int i;

    private  HandlerThread mHandlerThread = new HandlerThread("log_h_thread");

    private Handler mHandler;

    private static class SingletonHolder{
        private static LogUtil instance = new LogUtil();
    }
    private LogUtil(){
        init();
    }

    private void init(){
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public static LogUtil getInstance(){
        return SingletonHolder.instance;
    }


    public void d(String tag, String msg){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //if(!checkDebug())return;
                //Log.d(tag,msg);
                writeLog(tag,msg+"_"+i);
                i++;
            }
        });
    }

    protected boolean checkDebug(){
        return isApkInDebug(MApplication.getContext());
    }

    /**
     * 为避免产生大量垃圾日志文件，引入一个常量来决定是否需要打印日志
     * @param msg 打印的日志消息
     */
    private void writeLog(String tag, String msg) {
            //保存到的文件路径
            FileWriter fw;
            BufferedWriter bw = null;

            if(actionNum <= 0){
                processName = getProcessName();
                actionNum = 1;
            }

            try {
                //创建文件夹
                File dir = MApplication.getContext().getExternalFilesDir("log");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //创建文件
                File file = new File(dir, "mesdebug.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                //写入日志文件
                fw = new FileWriter(file, true);
                bw = new BufferedWriter(fw);
                bw.write(parseTime() +"  "+processName+ "   "+tag+"  "+ msg + "\n");
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("mtest","writelog e="+e.getMessage());
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

    }

    protected void delete(){
        try {
            //创建文件夹
            File dir = MApplication.getContext().getExternalFilesDir("log");
            if (!dir.exists()) {
                return;
            }
            //创建文件
            File file = new File(dir, "mesdebug.txt");
            if (!file.exists()) {
                return;
            }
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mtest","writelog e="+e.getMessage());
        }
    }

    private String getProcessName(){
        try{
            Class aClass = Class.forName("android.app.ActivityThread");
            Method currentProcessName = aClass.getDeclaredMethod("currentProcessName",new Class[0]);
            Log.d("mtest","getProcessName currentProcessName="+currentProcessName);
            if(currentProcessName == null){
                return "pid ="+android.os.Process.myPid();
            }
            String processName = (String) currentProcessName.invoke(null);
            Log.d("mtest","getProcessName processName="+processName);
            return processName;
        }catch (Exception e){
            e.printStackTrace();
            Log.d("mtest","getProcessName error="+e.getMessage());
            return "pid ="+android.os.Process.myPid();
        }
    }

    private String parseTime(){
        if(sdf == null) {
            synchronized (LogUtil.class) {
                if(sdf == null) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
                }
            }
        }
        return sdf.format(System.currentTimeMillis());
    }

    private static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }


}
