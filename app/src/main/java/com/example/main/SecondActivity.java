package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.main.R;
import com.example.main.log.LogManager;
import com.example.main.log.LogUtil;
import com.example.main.log.MessageClient;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("mtest","SecondActivity");

        findViewById(R.id.tv).postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 8000; i++) {
                    Log.d("mtest","SecondActivity write log="+i);
                    //LogManager.getInstance().d("mtest",ProcessUtil.getCurrentProcessNameByApplication()+"  i = "+i);
                    LogUtil.getInstance().d("mtest",ProcessUtil.getCurrentProcessName(SecondActivity.this)+"  si = "+i);
/*                    try{
                        Thread.sleep(4,999000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                }
            }
        },300);
    }
}