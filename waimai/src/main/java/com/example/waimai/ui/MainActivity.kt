package com.example.waimai.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.router.SimpleRouter
import com.example.router.compiler.annotation.MRoute
import com.example.waimai.BuildConfig
import com.example.waimai.databinding.ActivityTakeoutMainBinding

@MRoute("/aa/MainActivity")
class MainActivity : AppCompatActivity() {

    private var binding: ActivityTakeoutMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeoutMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)//setContentView(R.layout.activity_main)

        //val btnToSunrise = //findViewById<Button>(R.id.btn_skip_sunrise)
        binding!!.btnSkipSunrise.setOnClickListener {
            val clazz = SimpleRouter.navigation("/main/SunriseActivity")
            clazz?.let {
                startActivity(Intent(this, it))
            }
        }
    }
}



