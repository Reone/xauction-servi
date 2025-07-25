package com.reone.xauction.bean.vo

import com.reone.xauction.bean.po.UserPo
import com.reone.xauction.util.number2str

/**
 *
 * @author reone
 * @date 2025/7/17 16:45
 */
data class UserVo(
    val id: Long?,
    val code: String?,
    val name: String?,
    val nick: String?,
    val phone: String?,
    val avatar: String?,
    val role: Int?,
) {
    constructor(pojo: UserPo) : this(pojo.id, pojo.code, pojo.name, pojo.nick, pojo.phone, pojo.avatar, pojo.role)

    val no: String
        get() = '#' + number2str(id ?: 0L, 4)
}