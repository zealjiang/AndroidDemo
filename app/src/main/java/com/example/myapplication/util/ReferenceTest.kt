package com.example.myapplication.util

import android.util.Log
import com.example.myapplication.viewmodel.User
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

object ReferenceTest {
    val TAG = "ReferenceTest"
    fun weakReferenceTest() {
        Log.d(TAG, "weakReferenceTest ---")

        val referenceQueue = ReferenceQueue<Thread?>()
        var thread: Thread ?= Thread()
        var weakReference = WeakReference(thread, referenceQueue)

        Runtime.getRuntime().gc()//System.gc()//在Android环境中要使用Runtime.getRuntime().gc()； 在jvm虚拟机中运行使用System.gc()；如果在android环境中使用System.gc()没有任何效果
        try {
            Thread.sleep(10)
        } catch (e: Exception) {

        }

        var referenceThread = weakReference.get()
        Log.d(TAG, "1 referenceQueue = ${referenceQueue.poll()}," +
                " thread =$thread," +
                " referenceThread =$referenceThread," +
                " weakReference =$weakReference")
        //由打印可知，

        thread = null
        //如果这里不置为null,则referenceThread又指向了thread这个强引用，要知道referenceThread指向了thread,即使上面将thread = null，
        // 因为thread还有referenceThread变量引用，所以不会被gc回收的，只有当thread被弱引用或虚引用持有时，才能在gc时被回收
        //referenceThread = null
        Runtime.getRuntime().gc() //在Android环境中要使用Runtime.getRuntime().gc()； 在jvm虚拟机中运行使用System.gc()
        Thread.sleep(10)
        referenceThread = weakReference.get()
        Log.d(TAG, "2 referenceQueue = ${referenceQueue.poll()}," +
                " thread =$thread," +
                " referenceThread =$referenceThread," +
                " weakReference =$weakReference")
    }
}