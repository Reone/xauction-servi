package com.reone.xauction.controller

import com.reone.xauction.bean.dto.UserDto
import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.bean.vo.UserVo
import com.reone.xauction.service.UserService
import com.reone.xauction.util.ErrorCode
import com.reone.xauction.util.HEADER_USER_ID
import com.reone.xauction.util.Resp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController : BaseController() {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/genderCode")
    fun genderCode(): RespVo<String> {
        return Resp.success(userService.genderCode())
    }

    @PostMapping("/loginByCode")
    fun login(code: String?): RespVo<UserVo> {
        return if (code == null) {
            Resp.fail("请填写8位邀请码", ErrorCode.ERR_CODE_PARAM)
        } else {
            Resp.success(userService.loginByCode(code))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDto): RespVo<UserVo> {
        return Resp.success(userService.register(userDto))
    }

    @PostMapping("/update")
    fun update(@RequestHeader(HEADER_USER_ID) id: Long?, @RequestBody userDto: UserDto): RespVo<UserVo> {
        if (id == null) {
            return Resp.fail("缺少用户id", ErrorCode.ERR_CODE_PARAM)
        } else {
            userDto.id = id
            return Resp.success(userService.update(userDto))
        }
    }
}