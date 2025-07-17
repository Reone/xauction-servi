package com.reone.xauction.bean.vo

/**
 *
 * @author reone
 * @date 2025/7/17 16:36
 */
open class RespVo<T>(
    val code: Int,
    val msg: String? = null,
    val data: T? = null,
)