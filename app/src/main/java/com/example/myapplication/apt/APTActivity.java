package com.example.myapplication.apt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.lib.processor.BindView;

public class APTActivity extends AppCompatActivity {

    @BindView(R.id.text_view1)
    TextView textView1;

    @BindView(R.id.text_view2)
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt);

        Butterknife.bind(this);

        textView1.setText("Annotation 1");
        textView2.setText("Annotation 2");

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(APTActivity.this,APTActivity2.class));
            }
        });
    }
}