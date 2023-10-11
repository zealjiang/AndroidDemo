package com.example.myapplication.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build

object ShortcutUtil {

    /**
     * 创建桌面快捷方式
     * @param context
     * @param iconResId
     * @param packageName
     * @param className
     */
    fun createShortcut(context: Context, iconResId: Int, packageName: String, className: String, label: String) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            // 当前版本大于 Android 7.1
            // 执行相应操作

            val shortcutIntent = Intent()
            shortcutIntent.setClassName(packageName, className)
            shortcutIntent.action = Intent.ACTION_VIEW
            shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER)

            val shortcutManager = context.getSystemService(ShortcutManager::class.java)

            //创建固定快捷方式
            if (shortcutManager.isRequestPinShortcutSupported) {

                val shortcutInfo = ShortcutInfo.Builder(context, label+label.hashCode())
                    .setShortLabel(label)
                    //.setLongLabel("Open the $label")
                    .setIcon(Icon.createWithResource(context, iconResId))//R.drawable.shortcut_icon))
                    .setIntent(shortcutIntent)
                    .build()

                //用于为第二个参数创建Intent，这将导致应用图标显示在快捷方式的角落
                val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(shortcutInfo)

                val successCallback = PendingIntent.getBroadcast(
                    context,
                    0,
                    pinnedShortcutCallbackIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                shortcutManager.requestPinShortcut(shortcutInfo, successCallback.intentSender)
            }


/*            //创建动态快捷方式---创建不成功
            val shortcut = ShortcutInfoCompat.Builder(context, label+label.hashCode())
                .setShortLabel(label)
                .setLongLabel("Open the $label")
                .setIcon(IconCompat.createWithResource(context, iconResId))
                .setIntent(shortcutIntent)
                .build()



            val list = ArrayList<ShortcutInfoCompat?>()
            list.add(shortcut)
            //ShortcutManagerCompat.addDynamicShortcuts(context, list)
            ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)*/

        } else {
            // 当前版本小于等于 Android 7.1
            // 执行其他操作

            val shortcutIntent = Intent()
            shortcutIntent.setClassName(packageName, className)
            shortcutIntent.action = Intent.ACTION_MAIN
            shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER)

            val installShortcutIntent = Intent()
            installShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            installShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, label)
            installShortcutIntent.putExtra(
                Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, iconResId)//R.drawable.shortcut_icon)
            )
            installShortcutIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
            context.sendBroadcast(installShortcutIntent)

        }
    }


}