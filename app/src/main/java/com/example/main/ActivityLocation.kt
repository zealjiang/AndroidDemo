package com.example.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.main.util.LocationUtil


class ActivityLocation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location)

        val tvLog: TextView = findViewById(R.id.tv_log)

        //wifi
        findViewById<Button>(R.id.btn_wifi_is_open).setOnClickListener {
            val isWifiOpen = LocationUtil.isWifiOpen(this)
            tvLog.text = "wifi is open :$isWifiOpen"
        }

        findViewById<Button>(R.id.btn_wifi_open).setOnClickListener {
            if (!LocationUtil.isWifiOpen(this)) {
                LocationUtil.openWifiSettingPage(this)
            }
        }

        //gps
        findViewById<Button>(R.id.btn_gps_is_open).setOnClickListener {
            val isOpen = LocationUtil.isGpsOpen(this)
            tvLog.text = "gps is open :$isOpen"
        }

        findViewById<Button>(R.id.btn_gps_open).setOnClickListener {
            LocationUtil.openGpsSettingPage(this)
        }
    }


}
