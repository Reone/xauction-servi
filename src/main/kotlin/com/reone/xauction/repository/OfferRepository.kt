package com.reone.xauction.repository

import com.reone.xauction.bean.po.OfferPo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface OfferRepository : JpaRepository<OfferPo, Long>, JpaSpecificationExecutor<OfferPo> {
}