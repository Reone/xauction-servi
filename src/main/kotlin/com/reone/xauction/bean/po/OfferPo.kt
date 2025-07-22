package com.reone.xauction.bean.po

import javax.persistence.*

/**
 * @author reone
 * @date 2025/7/17 16:43
 * 出价
 */
@Entity
@Table(name = "xa_offer")
open class OfferPo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,

    @Column(nullable = false)
    open var auctionId: Long? = null,

    @Column(nullable = false)
    open var userId: Long? = null,

    @Column(nullable = true)
    open var price: Double? = null,

    /**
     * 是否中标 0:未中 1:中
     */
    @Column(nullable = false)
    open var win: Int? = 0,

    ) : BasePo()