package com.reone.xauction.controller


import com.reone.xauction.bean.dto.AuctionDto
import com.reone.xauction.bean.vo.AuctionVo
import com.reone.xauction.bean.vo.RespVo
import com.reone.xauction.service.AuctionService
import com.reone.xauction.service.StorageService
import com.reone.xauction.util.Resp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/auction")
class AuctionController : BaseController() {
    @Autowired
    lateinit var auctionService: AuctionService

    @Autowired
    lateinit var storageService: StorageService

    @GetMapping("/list")
    fun list(auctionDto: AuctionDto): RespVo<List<AuctionVo>> {
        return Resp.success(auctionService.list(auctionDto))
    }

    @GetMapping("/info")
    fun info(id: Long): RespVo<AuctionVo> {
        return Resp.success(auctionService.info(id))
    }

    @PostMapping("/uploadImages", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImages(@RequestPart("files") files: List<MultipartFile>): RespVo<List<String>> {
        return Resp.success(storageService.uploadAuctionImages(files))
    }

    @PostMapping("/add")
    fun add(@RequestBody auction: AuctionDto): RespVo<AuctionVo> {
        return Resp.success(auctionService.add(auction))
    }

    @PostMapping("/update")
    fun update(@RequestBody auction: AuctionDto): RespVo<AuctionVo> {
        if (auction.id == null) {
            return Resp.fail("id不能为空")
        }
        return Resp.success(auctionService.update(auction))
    }

    @PostMapping("/delete")
    fun delete(id: Long): RespVo<Boolean> {
        return Resp.success(auctionService.delete(id))
    }
}