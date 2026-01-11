package com.reone.xauction.controller

import com.reone.xauction.bean.dto.UserDto
import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.bean.vo.UserVo
import com.reone.xauction.service.UserService
import com.reone.xauction.util.ErrorCode
import com.reone.xauction.util.Resp
import com.reone.xauction.util.currentUserId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController : BaseController() {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/generateCode")
    fun generateCode(): RespVo<String> {
        return Resp.success(userService.genderCode())
    }

    @GetMapping("/list")
    fun list(): RespVo<List<UserVo>> {
        return Resp.success(userService.list())
    }

    @GetMapping("/info")
    fun info(id: Long): RespVo<UserVo> {
        val user = userService.findById(id)
            ?: return Resp.fail("用户不存在", ErrorCode.ERR_CODE_PARAM)
        return Resp.success(user)
    }

    @PostMapping("/loginByCode")
    fun login(@RequestBody userDto: UserDto): RespVo<UserVo> {
        return if (userDto.code == null) {
            Resp.fail("请填写识别码", ErrorCode.ERR_CODE_PARAM)
        } else {
            Resp.success(userService.loginByCode(userDto.code!!))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDto): RespVo<UserVo> {
        return Resp.success(userService.register(userDto))
    }

    @PostMapping("/update")
    fun update(@RequestBody userDto: UserDto): RespVo<UserVo> {
        if (userDto.id == null) {
            userDto.id = currentUserId
        }
        return if (userDto.id == null) {
            Resp.fail("缺少用户id", ErrorCode.ERR_CODE_PARAM)
        } else {
            Resp.success(userService.update(userDto))
        }
    }

    @PostMapping("/delete")
    fun delete(id: Long?): RespVo<Boolean> {
        if (id == null) {
            return Resp.fail("缺少用户id", ErrorCode.ERR_CODE_PARAM)
        }
        return Resp.success(userService.delete(id))
    }
}
