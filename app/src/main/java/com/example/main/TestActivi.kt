package com.example.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class TestActivi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    private var cacheFragment: Fragment ?= null
    fun switchFragment(nowFragment: Fragment, toFragment: Fragment) {
        if (nowFragment != toFragment) {
            // 缓存即将切换的Fragment，以便下一次对比判断
            cacheFragment = toFragment;
            // 开启事物
            val ft = this.supportFragmentManager.beginTransaction()//getSupportFragmentManager().beginTransaction();
            // 判断即将切换的Fragment是否add过
            if (!toFragment.isAdded()) {
                // 初始无缓存，需判断
                if (nowFragment != null) {
                    // 隐藏当前Fragment
                    ft.hide(nowFragment);
                }
                // 添加即将切换的Fragment，并提交事物
                //ft.add(R.id.content_layout, toFragment).commit();
            } else {
                // 初始无缓存，需判断
                if (nowFragment != null) {
                    // 隐藏当前Fragment
                    ft.hide(nowFragment);
                }
                // 显示即将切换的Fragment，并提交事物
                ft.show(toFragment).commit();
            }
        }
    }
}