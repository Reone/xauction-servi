package com.reone.xauction.repository

import com.reone.xauction.bean.po.UserPo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserPo, Long>, JpaSpecificationExecutor<UserPo>{
    fun findByCode(code: String): UserPo?
}