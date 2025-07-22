package com.reone.xauction.util

/**
 *
 * @author reone
 * @date 2025/7/17 20:59
 */
object CodeHelper {
    fun generateCode(): String {
        //生成6位随机数组编码
        return (0..5).map {
            (Math.random() * 10).toInt()
        }.joinToString("")
    }
}