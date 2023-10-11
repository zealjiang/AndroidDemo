package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.util.AppUtil
import com.example.myapplication.util.ShortcutUtil
import com.example.myapplication.util.WallpaperUtil


class SunriseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunrise)


        val mSunriseView = findViewById<com.example.myapplication.views.SunriseView>(R.id.sunrise_view)
        val btnAnim = findViewById<Button>(R.id.btn_anim)
        btnAnim.setOnClickListener {
            mSunriseView.runAnim()
        }

/*        val intent = Intent(Settings.ACTION_SETTINGS)
        val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)

        val packageName = resolveInfo!!.activityInfo.packageName
        val className = resolveInfo!!.activityInfo.name

        Log.d("SunriseActivity", "packageName =$packageName, className = $className")*/

        ShortcutUtil.createShortcut(this, R.drawable.sun,
            "com.android.settings",
            "com.android.settings.homepage.SettingsHomepageActivity",
            "设置")

/*        ShortcutUtil.createShortcut(this, R.drawable.sun, "com.android.settings", "com.android.settings.Settings", "设置")


        ShortcutUtil.createShortcut(this, R.drawable.sun,
            "com.android.settings",
            "com.android.settings.MiuiSettings",
            "设置")*/
        //AppUtil.getAppPackageList(this)

        //WallpaperUtil.setLockScreenWallpaper(this, R.drawable.wallpaper_background)
        //WallpaperUtil.setScreenWallpaper(this, R.drawable.wallpaper_background)
        //WallpaperUtil.setLockAndScreenWallpaper(this, R.drawable.wallpaper_background)

    }

}