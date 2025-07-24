package com.reone.xauction.bean.vo

import com.reone.xauction.bean.po.AuctionPo

/**
 *
 * @author reone
 * @date 2025/7/22 22:20
 */
data class AuctionVo(
    val id: Long? = null,
    val title: String? = null,
    val imgUrl: String? = null,
    val subTitle: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val startTime: Long? = null,
    val endTime: Long? = null,
    val status: Int? = 0,
    val offerId: Long? = null,
) {
    constructor(pojo: AuctionPo) : this(
        pojo.id,
        pojo.title,
        pojo.imgUrl,
        pojo.subTitle,
        pojo.minPrice,
        pojo.maxPrice,
        pojo.startTime,
        pojo.endTime,
        pojo.status,
        pojo.offerId
    )
}