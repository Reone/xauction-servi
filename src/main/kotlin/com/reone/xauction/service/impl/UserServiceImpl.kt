package com.reone.xauction.service.impl

import com.reone.xauction.bean.dto.UserDto
import com.reone.xauction.bean.po.UserPo
import com.reone.xauction.bean.vo.UserVo
import com.reone.xauction.repository.UserRepository
import com.reone.xauction.service.UserService
import com.reone.xauction.util.CodeHelper
import com.reone.xauction.util.currentTime
import com.reone.xauction.util.currentUserId
import com.reone.xauction.util.unixTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 *
 * @author reone
 * @date 2025/7/17 21:20
 */
@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun genderCode(): String {
        val code = CodeHelper.generateCode()
        val user = userRepository.findByCode(code)
        return if (user == null) {
            code
        } else {
            genderCode()
        }
    }

    override fun update(userDto: UserDto): UserVo {
        if (userDto.id == null) {
            throw Throwable("缺少id参数")
        }
        //只保存有值的字段
        val user = userRepository.findByIdOrNull(userDto.id!!) ?: throw Throwable("用户不存在")
        user.copyNotNull(userDto)
        user.updateTime = currentTime()
        user.updateBy = currentUserId.toString()
        val userPo = userRepository.save(userDto)
        return UserVo(userPo)
    }

    override fun register(userDto: UserDto): UserVo {
        if (userDto.code.isNullOrEmpty()) {
            throw Throwable("缺少code参数")
        }
        var user = userRepository.findByCode(userDto.code!!)
        if (user != null) {
            throw Throwable("用户已存在")
        }
        user = UserPo()
        user.code = userDto.code
        user.copyNotNull(userDto)
        if (user.nick.isNullOrEmpty()) {
            user.nick = "匿名用户${unixTime()}"
        }
        user.createTime = currentTime()
        user.createBy = currentUserId.toString()
        user.updateTime = user.createTime
        user.updateBy = currentUserId.toString()
        user = userRepository.save(user)
        return UserVo(user)
    }

    override fun loginByCode(code: String): UserVo {
        val user = userRepository.findByCode(code)
        if (user != null) {
            val updateUser = UserDto()
            updateUser.id = user.id
            updateUser.loginTime = currentTime()
            update(updateUser)
            return UserVo(user)
        } else {
            throw Throwable("用户不存在")
        }
    }
}