package com.example.myapplication.util

import android.util.Log
import java.text.DecimalFormat

class UnitUtil {
    private val TAG = "UnitUtil"
    fun unitChange(fileSize: Long, unit: Boolean): String? {
        if (fileSize == 0L) {
            return "0 MB"
        }

        Log.d(TAG, "fileSize $fileSize")
        var size: Float
        var i = 0
        val oneFormat = DecimalFormat("0.0")
        val twoFormat = DecimalFormat("0.00")
        if (fileSize >= 1024) {
            i++
            size = fileSize / 1024.0f
            while (size > 1024.0f) {
                i++
                size /= 1024.0f
                Log.d(TAG, "*****fileSize $fileSize  size =$size  i= $i")
            }
            Log.d(TAG, "fileSize $fileSize  size =$size  i= $i")
        } else {
            size = fileSize * 1.0f
        }
        if (unit) {
            if (size > 0 && size < 10) {
                return twoFormat.format(size.toDouble()) + " " + getUnit(i)
            } else if (size >= 10 && size < 100) {
                return oneFormat.format(size.toDouble()) + " " + getUnit(i)
            } else if (size >= 100) {
                return size.toInt().toString() + " " + getUnit(i)
            }
        }
        return twoFormat.format(size.toDouble())
    }

    fun getUnit(i: Int): String? {
        var strUnit = "byte"
        if (i == 1) {
            strUnit = "KB"
        } else if (i == 2) {
            strUnit = "MB"
        } else if (i == 3) {
            strUnit = "GB"
        }
        return strUnit
    }
}