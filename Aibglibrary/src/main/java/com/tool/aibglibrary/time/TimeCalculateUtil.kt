package com.tool.aibglibrary.time

import java.util.Calendar
import java.util.concurrent.TimeUnit

class TimeCalculateUtil {

    //计算两个时间撮之间的天数差异
    fun calculateDaysDifference(timestamp1: Long, timestamp2: Long): Int {
        // 创建 Calendar 实例并设置时间
        val calendar1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val calendar2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }

        // 将两个日期设置为同一天的0点0分0秒
        resetTime(calendar1)
        resetTime(calendar2)
        // 计算天数差异
        val millisecondsDifference = calendar1.timeInMillis - calendar2.timeInMillis

        return TimeUnit.MILLISECONDS.toDays(millisecondsDifference).toInt()
    }

    private fun resetTime(calendar: Calendar) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}