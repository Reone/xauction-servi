package com.reone.xauction.controller

import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.util.Resp
import org.springframework.web.bind.annotation.ExceptionHandler

abstract class BaseController {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): RespVo<String> {
        ex.printStackTrace()
        return Resp.fail(ex.message ?: "Unknown error occurred")
    }
}
