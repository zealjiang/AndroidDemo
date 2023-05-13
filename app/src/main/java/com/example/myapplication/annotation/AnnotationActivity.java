package com.example.myapplication.annotation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.apt.APTActivity2;
import com.example.myapplication.apt.Butterknife;

public class AnnotationActivity extends AppCompatActivity {

/*    @BindView(R.id.text_view1)
    TextView textView1;

    @BindView(R.id.text_view2)
    TextView textView2;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        //setContentView(R.id.text_view1);

        String fullName = this.getClass().getCanonicalName();
        Log.d("mtest","fullName ="+fullName);
        //Butterknife.bind(this);
        ButterknifeClick.onClick2(this);

/*
        findViewById(R.id.text_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick",  "点击了 标题");
            }
        });*/
    }

    @ClickEvent({R.id.text_view1,R.id.text_view2})
    public void onClick(View view){
        Log.d("onClick","onClick 点击了 "+view.getId());
    }

    @LongClickEvent({R.id.text_view1,R.id.text_view2})
    public boolean onLongClick(View view){
        Log.d("onClick","onLongClick  点击了 "+view.getId());
        return true;
    }
}