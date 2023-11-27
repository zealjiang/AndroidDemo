package com.example.main.arouter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.main.R;

@Route(path = ArouterPathConfig.AROUTER_ACTIVITY2)
public class RActivityFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfragment);

        FrameLayout frameLayout = findViewById(R.id.container);

        ScrollingFragment scrollingFragment = new ScrollingFragment();
        Log.d("mtest","RActivityFragment scrollingFragment.hashcode = "+scrollingFragment.hashCode());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,scrollingFragment);
        fragmentTransaction.commit();
        TextView textView = findViewById(R.id.tv);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}