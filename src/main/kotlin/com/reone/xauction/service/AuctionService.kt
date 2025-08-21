package com.reone.xauction.service

import com.reone.xauction.bean.dto.AuctionDto
import com.reone.xauction.bean.vo.AuctionVo

/**
 *
 * @author reone
 * @date 2025/7/17 21:19
 */
interface AuctionService {
    fun list(auctionDto: AuctionDto): List<AuctionVo>

    fun info(id: Long): AuctionVo

    fun add(auction: AuctionDto): AuctionVo?

    fun update(auction: AuctionDto): AuctionVo?

    fun delete(id: Long): Boolean
}