package com.example.main.arouter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.main.R;
import com.example.main.bean.Person;

public class RActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ractivity1);

        //注入
//        ARouter.getInstance().inject(this);

        TextView textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Simple jump within application (Jump via URL in 'Advanced usage')
                ARouter.getInstance().build(ArouterPathConfig.AROUTER_ACTIVITY2).navigation();
                Fragment fragment = (Fragment) ARouter.getInstance().build(ArouterPathConfig.AROUTER_ACTIVITY_FRAGMENT).navigation();
                Log.d("mtest","RActivity1 fragment.hashCode ="+fragment.hashCode());

                // 2. Jump with parameters
/*                ARouter.getInstance().build(ArouterPathConfig.AROUTER_ACTIVITY2)
                        .withLong("key1", 666L)
                        .withString("key3", "888")
                        //.withObject("key4", new Person("Jack", "Rose"))
                        .navigation();*/
            }
        });
    }
}