package com.reone.xauction.bean.vo

import com.reone.xauction.bean.po.OfferPo

/**
 *
 * @author reone
 * @date 2025/7/22 22:22
 */
data class OfferVo(
    val id: Long? = null,
    val auctionId: Long? = null,
    var user: UserVo? = null,
    val price: Double? = null,
    val win: Int? = 0,
) {
    constructor(pojo: OfferPo) : this(pojo.id, pojo.auctionId, null, pojo.price, pojo.win)
}