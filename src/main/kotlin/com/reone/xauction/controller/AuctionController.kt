package com.reone.xauction.controller


import com.reone.xauction.bean.dto.AuctionDto
import com.reone.xauction.bean.vo.AuctionVo
import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.service.AuctionService
import com.reone.xauction.util.Resp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auction")
class AuctionController : BaseController() {
    @Autowired
    lateinit var auctionService: AuctionService

    @GetMapping("/list")
    fun list(auctionDto: AuctionDto): RespVo<List<AuctionVo>> {
        return Resp.success(auctionService.list(auctionDto))
    }

    @GetMapping("/info")
    fun info(id: Long): RespVo<AuctionVo> {
        return Resp.success(auctionService.info(id))
    }

    @PostMapping("/add")
    fun add(auction: AuctionDto): RespVo<Boolean> {
        return Resp.success(auctionService.add(auction))
    }

    @PostMapping("/update")
    fun update(auction: AuctionDto): RespVo<Boolean> {
        return Resp.success(auctionService.update(auction))
    }

    @PostMapping("/delete")
    fun delete(id: Long): RespVo<Boolean> {
        return Resp.success(auctionService.delete(id))
    }
}