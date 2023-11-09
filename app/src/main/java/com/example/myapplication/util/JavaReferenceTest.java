package com.example.myapplication.util;

import android.util.Log;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class JavaReferenceTest {

    private static String TAG = "JavaReferenceTest";
    public static void weakReferenceTest() {
        Log.d(TAG, "weakReferenceTest ---");

        Thread thread = new Thread();
        ReferenceQueue referenceQueue = new ReferenceQueue<Thread>();
        WeakReference weakReference = new WeakReference<Thread>(thread, referenceQueue);

        Runtime.getRuntime().gc();
        try {
            Thread.currentThread().sleep(10);
        } catch (Exception e) {

        }

        Object referenceThread = weakReference.get();
        Log.d(TAG, "1 referenceQueue = "+referenceQueue.poll() +
                " thread ="+ thread +
                " referenceThread ="+referenceThread  +
                " weakReference =" +weakReference);

        thread = null;
        referenceThread = null;
        Runtime.getRuntime().gc();
        try {
            Thread.currentThread().sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        referenceThread = weakReference.get();
        Log.d(TAG, "2 referenceQueue = "+referenceQueue.poll() +
                " thread ="+ thread +
                " referenceThread ="+referenceThread  +
                " weakReference =" +weakReference);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
