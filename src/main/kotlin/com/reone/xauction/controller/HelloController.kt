package com.reone.xauction.controller


import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.util.Resp
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController : BaseController() {
    @GetMapping("/touch")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String?): RespVo<String> {
        return Resp.success(String.format("hello %s", name))
    }
}