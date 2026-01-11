package com.reone.xauction.service

import com.reone.xauction.bean.dto.OfferDto
import com.reone.xauction.bean.vo.OfferVo

/**
 *
 * @author reone
 * @date 2025/7/17 21:19
 */
interface OfferService {
    fun list(offerDto: OfferDto): List<OfferVo>

    fun info(id: Long): OfferVo

    fun add(offer: OfferDto): OfferVo

    fun update(offer: OfferDto): OfferVo

    fun delete(id: Long): Boolean
}
