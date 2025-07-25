package com.reone.xauction.bean.po

import javax.persistence.Column
import javax.persistence.Entity

/**
 *
 * @author reone
 * @date 2025/7/17 16:39
 */
open class BasePo {
    @Column(nullable = false)
    open var createTime: Long = 0L

    @Column(nullable = false)
    open var createBy: String = ""

    @Column(nullable = false)
    open var updateTime: Long = 0L

    @Column(nullable = false)
    open var updateBy: String = ""
}