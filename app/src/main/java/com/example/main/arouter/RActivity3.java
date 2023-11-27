package com.example.main.arouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.main.R;

@Route(path = ArouterPathConfig.AROUTER_ACTIVITY3)
public class RActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ractivity3);

        //注入
        ARouter.getInstance().inject(this);
    }
}