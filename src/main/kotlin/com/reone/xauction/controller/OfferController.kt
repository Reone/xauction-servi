package com.reone.xauction.controller


import com.reone.xauction.bean.dto.OfferDto
import com.reone.xauction.bean.vo.OfferVo
import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.service.OfferService
import com.reone.xauction.util.ErrorCode
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

    @GetMapping("/info")
    fun info(id: Long): RespVo<OfferVo> {
        return Resp.success(offerService.info(id))
    }

    @PostMapping("/add")
    fun add(@RequestBody offer: OfferDto): RespVo<OfferVo> {
        return Resp.success(offerService.add(offer))
    }

    @PostMapping("/update")
    fun update(@RequestBody offer: OfferDto): RespVo<OfferVo> {
        if (offer.id == null) {
            return Resp.fail("缺少出价id", ErrorCode.ERR_CODE_PARAM)
        }
        return Resp.success(offerService.update(offer))
    }

    @PostMapping("/delete")
    fun delete(id: Long?): RespVo<Boolean> {
        if (id == null) {
            return Resp.fail("缺少出价id", ErrorCode.ERR_CODE_PARAM)
        }
        return Resp.success(offerService.delete(id))
    }
}
