package com.reone.xauction.bean.po

import java.time.LocalDate
import javax.persistence.*

/**
 * @author reone
 * @date 2025/7/17 16:43
 * 拍卖
 */
@Entity
@Table(name = "xa_auction")
open class AuctionPo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,

    @Column(nullable = true)
    open var title: String? = null,

    @Column(nullable = true)
    open var imgUrl: String? = null,

    @Column(nullable = true, length = 2048)
    open var subTitle: String? = null,

    @Column(nullable = true)
    open var minPrice: Double? = null,

    @Column(nullable = true)
    open var maxPrice: Double? = null,

    @Column(nullable = true)
    open var startTime: LocalDate? = null,

    @Column(nullable = true)
    open var endTime: LocalDate? = null,

    /**
     * 状态 0:草稿 1:未开始 2:进行中 3:中止 4.拍卖完成 5.流标
     */
    @Column(nullable = false)
    open var status: Int? = 0,

    /**
     * 拍卖成功的出价
     */
    @Column(nullable = true)
    open var offerId: Long? = null,

    ) : BasePo()