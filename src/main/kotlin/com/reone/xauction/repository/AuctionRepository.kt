package com.reone.xauction.repository

import com.reone.xauction.bean.po.AuctionPo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface AuctionRepository : JpaRepository<AuctionPo, Long>, JpaSpecificationExecutor<AuctionPo> {
}