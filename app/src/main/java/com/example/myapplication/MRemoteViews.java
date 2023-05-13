package com.example.myapplication;


import android.app.PendingIntent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;


import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;

public class MRemoteViews extends RemoteViews {
    public MRemoteViews(String packageName, int layoutId) {
        super(packageName, layoutId);
    }

/*    public void aa(){
        Class<?> classes = Class.forName("android.app.ActivityThread");
        Method activityThread = classes.getDeclaredMethod("currentActivityThread");
        activityThread.setAccessible(true);
        Object currentThread = activityThread.invoke(null);
        Field instrumentationField = classes.getDeclaredField("mInstrumentation");
        instrumentationField.setAccessible(true);
        Instrumentation instrumentationInfo = (Instrumentation) instrumentationField.get(currentThread);
        FixInstrumentation fixInstrumentation = new FixInstrumentation(instrumentationInfo);
        instrumentationField.set(currentThread, fixInstrumentation);
    }*/



    @Override
    public void setOnClickPendingIntent(int viewId, PendingIntent pendingIntent) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setOnClickResponse(viewId, FixRemoteResponse.fromPendingIntent(pendingIntent));
            Log.d("mtest","setOnClickPendingIntent pendingIntent="+pendingIntent.hashCode());
        }
    }

    public void setOnClickResponse(int viewId, RemoteResponse response) {
        try{
            Class classes = Class.forName("android.widget.RemoteViews");

/*            Field[] fields = classes.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Log.d("mtest","setOnClickResponse field="+fields[i]);
            }*/

            Field mActions = classes.getDeclaredField("mActions");
            mActions.setAccessible(true);
            //ArrayList<Action> actions = (ArrayList<Action>) mActions.get(this);
            //Log.d("mtest","setOnClickResponse actions="+actions);
            //actions.add(new SetOnClickResponse(viewId, response));

/*            Method addAction = classes.getDeclaredMethod("addAction");
            addAction.setAccessible(true);
            addAction.invoke(this,new SetOnClickResponse(viewId, response));*/


/*            Class[] classeArray = classes.getDeclaredClasses();
            for (int i = 0; i < classeArray.length; i++) {
                Log.d("mtest","setOnClickResponse class="+classeArray[i]);
                if("class android.widget.RemoteViews$Action".equals(classeArray[i])){

                    return;
                }
            }*/



        }catch (Exception e){
            e.printStackTrace();
            Log.d("mtest","setOnClickResponse e="+e.getMessage());
        }
        //addAction(new SetOnClickResponse(viewId, response));
    }

    public interface OnClickHandler{
        boolean onClickHandler(View view, PendingIntent pendingIntent, RemoteResponse response);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static class FixRemoteResponse extends RemoteViews.RemoteResponse {
        public FixRemoteResponse() {
            Log.d("mtest","FixRemoteResponse version="+Build.VERSION.SDK_INT);
        }

        public static RemoteResponse fromPendingIntent(PendingIntent pendingIntent) {
            RemoteViews.RemoteResponse fixRemoteResponse = new FixRemoteResponse();
            RemoteViews.RemoteResponse.fromPendingIntent(pendingIntent);
/*            try{
                Class<?> classes = Class.forName("android.widget.RemoteViews$RemoteResponse");//fixRemoteResponse.getClass().getSuperclass();
                Field mPendingIntentField = classes.getDeclaredField("mPendingIntent");
                mPendingIntentField.setAccessible(true);
                mPendingIntentField.set(fixRemoteResponse,pendingIntent);
                Log.d("mtest","fromPendingIntent over");
            }catch (Exception e){
*//*                e.printStackTrace();
                Log.d("mtest","fromPendingIntent e="+e.getMessage());*//*
            }
            //response.mPendingIntent = pendingIntent;*/
            return fixRemoteResponse;
        }
    }

}
