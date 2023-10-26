package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.databinding.ActivityDataBindingBinding

class DataBindingActivity : FragmentActivity() {


    private var bindingActivity: ActivityDataBindingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityDataBindingBinding.inflate(layoutInflater)
        setContentView(bindingActivity?.root)

        bindingActivity?.tvTitle?.text = "DataBindingActivity"
        bindingActivity?.ivSun?.setImageResource(R.drawable.wallpaper_background)
    }
}