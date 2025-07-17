package com.reone.xauction.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by reone on 2020/12/28.
 * desc:时间相关的工具类
 */

private var deviation = 0L //网络时间与当前系统时间的偏差

/**
 * 时间戳保存map
 */
val timesHolder = hashMapOf<String, Long>()

const val TIME_ONE_DAY = 24 * 60 * 60 * 1000L
const val TIME_ONE_HOUR = 60 * 60 * 1000L
const val TIME_HALF_HOUR = 30 * 60 * 1000L

/**
 * 判断两个时间是否在同一天
 */
fun sameDay(time: Long, otherTime: Long): Boolean {
    val day = printTime(time, "yyyyMMdd")
    val otherDay = printTime(otherTime, "yyyyMMdd")
    return day == otherDay
}

/**
 * 矫正当前时间
 */
fun correctionTime(time: Long) {
    val temp = System.currentTimeMillis() - time
    if (temp > TIME_ONE_HOUR) {
        deviation = temp
    }
}


/**
 * 获取当前时间
 */
fun currentTime(): Long {
    return System.currentTimeMillis() - deviation
}

/**
 * 秒级时间戳
 */
fun unixTime(): Long {
    return currentTime() / 1000
}

fun currentDate(): Calendar {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.time = Date(currentTime())
    return calendar
}

/**
 * 获取某一天开始的毫秒数
 */
fun startTimeAt(calendar: Calendar): Long {
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun printTime(time: Long?, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    if (time == null) {
        return ""
    }
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(time))
}

/**
 * 将指定毫秒数 转换为时分秒倒计时时间字串
 */
fun remainingTime(long: Long?, showDay: Boolean = true, moreDayDetail: Boolean = false): String {
    long?.let { time ->
        val seconds: Long = time / 1000 % 60
        val minute: Long = time / 1000 / 60 % 60
        var hour: Long = time / 1000 / 60 / 60
        var temp = ""
        if (showDay) {
            hour = time / 1000 / 60 / 60 % 24
            val day: Long = time / 1000 / 60 / 60 / 24
            if (day > 0) {
                temp = if (moreDayDetail) {
                    "${day}d ${hour}h ${if (minute > 10) minute else "0${minute}"}m"
                } else {
                    "$day days left "
                }
                return temp
            }
        }
        temp = if (hour > 0) {
            if (hour < 10) {
                temp + "0" + hour + ":"
            } else {
                "$temp$hour:"
            }
        } else {
            "00:"
        }
        temp = if (minute < 10) {
            temp + "0" + minute + ":"
        } else {
            "$temp$minute:"
        }
        temp = if (seconds < 10) {
            temp + "0" + seconds
        } else {
            temp + seconds
        }
        return temp
    }
    return "00:00:00"
}