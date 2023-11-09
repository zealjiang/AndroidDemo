package com.example.takeout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.router.SimpleRouter
import com.example.router.compiler.annotation.MRoute
import com.example.takeout.databinding.ActivityTakeoutMainBinding

@MRoute("/takeout/MainActivity")
class MainActivity : AppCompatActivity() {

    private var binding: ActivityTakeoutMainBinding ?= null

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



