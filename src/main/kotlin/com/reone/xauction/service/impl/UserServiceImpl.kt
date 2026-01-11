package com.reone.xauction.service.impl

import com.reone.xauction.bean.dto.UserDto
import com.reone.xauction.bean.po.UserPo
import com.reone.xauction.bean.vo.UserVo
import com.reone.xauction.repository.UserRepository
import com.reone.xauction.service.UserService
import com.reone.xauction.util.CodeHelper
import com.reone.xauction.util.currentTime
import com.reone.xauction.util.operatorId
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
        user.updateBy = operatorId
        val userPo = userRepository.save(user)
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
        user.role = 0
        if (user.nick.isNullOrEmpty()) {
            user.nick = "匿名用户${unixTime()}"
        }
        val now = currentTime()
        user.createTime = now
        user.createBy = operatorId
        user.updateTime = now
        user.updateBy = operatorId
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

    override fun findById(id: Long): UserVo? {
        val user = userRepository.findByIdOrNull(id)
        return user?.let { UserVo(user) }
    }

    override fun list(): List<UserVo> {
        val userList = userRepository.findAll()
        return userList.map { UserVo(it) }
    }

    override fun delete(id: Long): Boolean {
        val user = userRepository.findByIdOrNull(id) ?: throw Throwable("用户不存在")
        userRepository.delete(user)
        return true
    }
}
