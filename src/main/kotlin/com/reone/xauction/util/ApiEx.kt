package com.reone.xauction.util

import com.reone.xauction.bean.vo.RespVo
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author reone
 * @date 2025/7/17 16:35
 */

const val HEADER_USER_ID = "user-id"

private fun getRequestAttributes(): ServletRequestAttributes? {
    val attributes = RequestContextHolder.getRequestAttributes()
    return attributes as ServletRequestAttributes?
}

/**
 * 获取request
 */
val request: HttpServletRequest?
    get() = getRequestAttributes()?.request

/**
 * 获取response
 */
val response: HttpServletResponse?
    get() = getRequestAttributes()?.response

val currentUserId: Long?
    get() = request?.getHeader(HEADER_USER_ID)?.toLongOrNull()


object Resp {

    fun <T> success(data: T?, msg: String? = null): RespVo<T> {
        return RespVo(ErrorCode.ERR_CODE_SUCCESS, msg, data)
    }

    fun <T> fail(msg: String?, code: Int = ErrorCode.ERR_CODE_IDLE): RespVo<T> {
        return RespVo(code, msg, null)
    }
}

object ErrorCode {
    const val ERR_CODE_SUCCESS = 200
    const val ERR_CODE_IDLE = 10000
    const val ERR_CODE_PARAM = 10001
    const val ERR_CODE_VERIFY = 10002
}