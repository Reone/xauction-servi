package com.reone.xauction.util

/**
 *
 * @author reone
 * @date 2025/7/17 22:24
 */

/**
 * 根据数字生成指定位数的字符串，前面拼0
 */
fun number2str(number: Number, length: Int): String {
    val str = number.toString()
    return if (str.length >= length) {
        str
    } else {
        "0".repeat(length - str.length) + str
    }
}

