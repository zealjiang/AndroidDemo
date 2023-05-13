package com.example.myapplication.apt;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.processor.BindView;
import com.example.myapplication.R;

public class APTActivity2 extends AppCompatActivity {

    @BindView(R.id.text_view1)
    TextView textView1;

    @BindView(R.id.text_view2)
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt2);

        Butterknife.bind(this);

        textView1.setText("Annotation 3");
        textView2.setText("Annotation 4");

        textView1.getLayoutParams().width = (int)(getResources().getDisplayMetrics().density * 250);
    }
}