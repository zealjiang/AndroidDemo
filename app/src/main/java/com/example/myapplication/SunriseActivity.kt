package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.nfc.NFCBankCardActivity
import com.example.router.compiler.annotation.MRoute

@MRoute("/main/SunriseActivity")
class SunriseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunrise)


        val mSunriseView = findViewById<com.example.myapplication.views.SunriseView>(R.id.sunrise_view)
        val btnAnim = findViewById<Button>(R.id.btn_anim)
        btnAnim.setOnClickListener {
            mSunriseView.runAnim()
        }

        //startActivity(Intent(this, NFCBankCardActivity::class.java))

/*        val intent = Intent(Settings.ACTION_SETTINGS)
        val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)

        val packageName = resolveInfo!!.activityInfo.packageName
        val className = resolveInfo!!.activityInfo.name

        Log.d("SunriseActivity", "packageName =$packageName, className = $className")*/

/*        ShortcutUtil.createShortcut(this, R.drawable.sun,
            "com.android.settings",
            "com.android.settings.homepage.SettingsHomepageActivity",
            "设置")*/

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