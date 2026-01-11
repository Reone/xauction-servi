package com.reone.xauction.bean.dto

import java.time.LocalDate

/**
 * 拍卖请求/查询参数
 */
class AuctionDto {
    var id: Long? = null
    var title: String? = null
    var imgUrl: String? = null
    var imgUrls: List<String>? = null
    var subTitle: String? = null
    var minPrice: Double? = null
    var maxPrice: Double? = null
    var startTime: LocalDate? = null
    var endTime: LocalDate? = null
    var status: Int? = null
    var offerId: Long? = null
}
