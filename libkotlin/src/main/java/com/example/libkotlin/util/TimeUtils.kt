package com.example.libkotlin.util

import java.util.*

object TimeUtils {
    fun isToday(date: Date): Boolean {
        return isToday(date.time)
    }

    /**
     * Return whether it is today.
     *
     * @param millis The milliseconds.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isToday(millis: Long): Boolean {
        val wee: Long = getWeeOfToday()
        return millis >= wee && millis < wee + TimeConstants.DAY
    }

    private fun getWeeOfToday(): Long {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }
}