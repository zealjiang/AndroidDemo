package com.example.main.util

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import java.io.IOException


object WallpaperUtil {

    /**
     * 设置锁屏壁纸
     */
    fun setLockScreenWallpaper(context: Context, drawableId: Int) {
        // 设置锁屏壁纸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val wallpaperManager = WallpaperManager.getInstance(context)
            try {
                // 获取要设置的图片资源
                val lockScreenBitmap = BitmapFactory.decodeResource(context.resources, drawableId)

                // 设置锁屏壁纸
                wallpaperManager?.setBitmap(lockScreenBitmap, null, true, WallpaperManager.FLAG_LOCK)

                // 释放位图资源
                lockScreenBitmap.recycle()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 设置桌面壁纸
     */
    fun setScreenWallpaper(context: Context, drawableId: Int) {
        // 设置桌面壁纸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val wallpaperManager = WallpaperManager.getInstance(context)
            try {
                // 获取要设置的图片资源
                val lockScreenBitmap = BitmapFactory.decodeResource(context.resources, drawableId)

                // 设置桌面壁纸
                wallpaperManager?.setBitmap(lockScreenBitmap, null, true, WallpaperManager.FLAG_SYSTEM)

                // 释放位图资源
                lockScreenBitmap.recycle()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 设置锁屏和桌面壁纸
     */
    fun setLockAndScreenWallpaper(context: Context, drawableId: Int) {
        // 设置桌面壁纸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val wallpaperManager = WallpaperManager.getInstance(context)
            try {
                // 获取要设置的图片资源
                val lockScreenBitmap = BitmapFactory.decodeResource(context.resources, drawableId)

                // 设置锁屏壁纸
                wallpaperManager?.setBitmap(lockScreenBitmap, null, true, WallpaperManager.FLAG_LOCK)

                // 设置桌面壁纸
                wallpaperManager?.setBitmap(lockScreenBitmap, null, true, WallpaperManager.FLAG_SYSTEM)

                // 释放位图资源
                lockScreenBitmap.recycle()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 设置锁屏和桌面壁纸
     */
    fun setLockAndScreenWallpaper(context: Context, path: String) {
        // 设置桌面壁纸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val wallpaperManager = WallpaperManager.getInstance(context)
            try {
                // 获取要设置的图片资源
                val lockScreenBitmap = BitmapFactory.decodeFile(path)

                // 设置锁屏壁纸
                wallpaperManager?.setBitmap(lockScreenBitmap, null, true, WallpaperManager.FLAG_LOCK)

                // 设置桌面壁纸
                wallpaperManager?.setBitmap(lockScreenBitmap, null, true, WallpaperManager.FLAG_SYSTEM)

                // 释放位图资源
                lockScreenBitmap.recycle()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getBitmapFromPicture() {
        // 在 Activity 中定义一个请求码
        val PICK_IMAGE_REQUEST = 1

        // 在某个方法中调用以下代码来打开相册
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        //startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
}