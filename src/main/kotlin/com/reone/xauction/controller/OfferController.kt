package com.reone.xauction.controller


import com.reone.xauction.bean.dto.OfferDto
import com.reone.xauction.bean.vo.OfferVo
import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.service.OfferService
import com.reone.xauction.util.Resp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/offer")
class OfferController : BaseController() {
    @Autowired
    lateinit var offerService: OfferService

    @GetMapping("/list")
    fun list(offerDto: OfferDto): RespVo<List<OfferVo>> {
        return Resp.success(offerService.list(offerDto))
    }

    @PostMapping("/add")
    fun add(offer: OfferDto): RespVo<Boolean> {
        return Resp.success(offerService.add(offer))
    }

    @PostMapping("/update")
    fun update(offer: OfferDto): RespVo<Boolean> {
        return Resp.success(offerService.update(offer))
    }

    @PostMapping("/delete")
    fun delete(id: Long): RespVo<Boolean> {
        return Resp.success(offerService.delete(id))
    }
}