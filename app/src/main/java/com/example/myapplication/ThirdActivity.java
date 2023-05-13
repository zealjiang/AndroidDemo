package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.log.LogUtil;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("mtest","SecondActivity");

        TextView tv = findViewById(R.id.tv);
        tv.setText("ThirdActivity");
        tv.postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 3000; i < 4000; i++) {
                    //LogManager.getInstance().d("mtest",ProcessUtil.getCurrentProcessNameByApplication()+"  i = "+i);
                    LogUtil.getInstance().d("mtest",ProcessUtil.getCurrentProcessName(ThirdActivity.this)+"  i = "+i);
/*                    try{
                        Thread.sleep(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                }
            }
        },300);
    }
}