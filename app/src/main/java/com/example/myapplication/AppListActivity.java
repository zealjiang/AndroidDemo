package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.StackView;

import com.example.myapplication.adapter.AppRecyclerAdapter;
import com.example.myapplication.arouter.RActivity1;
import com.example.myapplication.bean.CacheListItem;
import com.example.myapplication.util.AppUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private final int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        startActivity(new Intent(this, RActivity1.class));

        mRecyclerview = findViewById(R.id.recyclerview);

        List<CacheListItem> datas = AppUtil.INSTANCE.getNotSystemAppPackageList(this);
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        AppRecyclerAdapter adapter = new AppRecyclerAdapter(this, datas);
        adapter.setOnClickListener(new AppRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("mtest","onClick positon ="+position);
                readAppPrivateFileTest(true);
            }
        });
        mRecyclerview.setAdapter(adapter);

        double totalMemeory = getTotalMemory(this);
        double availableMemeory = getAvailableMemory(this);
        Log.d("mtest","totalMemeory = "+totalMemeory+" Mb");
        Log.d("mtest","availableMemeory = "+availableMemeory+" Mb");
    }

    private void readAppPrivateFileTest(boolean needCheck){
        if(false && needCheck) {
            //测试一：授予sdcard的读写权限
            boolean boo = requestPermissions(this);
            if(!boo) {
                return;
            }
        }
        //读取微信的私有目录下的文件信息
        String fileDir1 = "/sdcard/Android/data/com.tencent.mm/MicroMsg";
        String fileDir2 = "/sdcard/Android/data/com.tencent.mm/cache";

        File cache = new File(fileDir1);
        boolean canRead = cache.canRead();
        if (!canRead) {
            Log.d("mtest",fileDir1+" can not read");
            //return;
        }
        if(cache.exists()){
            Log.d("mtest",fileDir1+" exist");
            long lastModified = cache.lastModified();//long lastModified() 返回此抽象路径名表示的文件最后一次被修改的时间。
            if(lastModified == 0){
                //可能是I/O异常，也可能是文件的创建时间
                try {
                    Path path = Paths.get(fileDir1);
                    BasicFileAttributes fileAttributes = Files.readAttributes(path, PosixFileAttributes.class);
                    if(fileAttributes == null){
                        Log.d("mtest",fileDir1+" fileAttributes == null");
                        return;
                    }
                    lastModified = fileAttributes.lastModifiedTime().toMillis();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String formatTime = formatter.format(lastModified);
            Log.d("mtest","lastModified ="+lastModified+"  formatTime ="+formatTime);

        }else{
            Log.d("mtest",fileDir1+" not exist");
        }
    }

    private boolean requestPermissions(Activity context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //没有权限则申请权限
                ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
                return false;
            }else {
                //有权限直接执行,docode()不用做处理
                return true;
            }

        }else {
            //小于6.0，不用申请权限，直接执行
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == REQUEST_CODE){
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                    &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意使用write
                readAppPrivateFileTest(false);
            }else{
                //用户不同意，自行处理即可
                readAppPrivateFileTest(true);
            }
        }
    }

    public static ActivityManager.MemoryInfo getMemoryInfo(@NonNull Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi;
    }

    public static double getTotalMemory(@NonNull Context context) {
        return getMemoryInfo(context).totalMem * 1.0/ (1024 * 1024);
    }

    public static double getAvailableMemory(@NonNull Context context) {
        return getMemoryInfo(context).availMem * 1.0 / (1204 * 1024);
    }
}