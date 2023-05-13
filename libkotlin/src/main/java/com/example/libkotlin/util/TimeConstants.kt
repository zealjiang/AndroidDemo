package com.example.libkotlin.util

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class TimeConstants {

    companion object{
        const val MSEC = 1
        const val SEC = 1000
        const val MIN = 60000
        const val HOUR = 3600000
        const val DAY = 86400000
    }

    @IntDef(MSEC, SEC, MIN, HOUR, DAY)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Unit
}