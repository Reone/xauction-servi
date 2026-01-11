package com.reone.xauction.service

import com.reone.xauction.bean.dto.UserDto
import com.reone.xauction.bean.vo.UserVo

/**
 *
 * @author reone
 * @date 2025/7/17 21:19
 */
interface UserService {
    fun genderCode(): String

    fun update(userDto: UserDto): UserVo

    fun loginByCode(code: String): UserVo

    fun register(userDto: UserDto): UserVo

    fun findById(id: Long): UserVo?
    fun list(): List<UserVo>
    fun delete(id: Long): Boolean
}
