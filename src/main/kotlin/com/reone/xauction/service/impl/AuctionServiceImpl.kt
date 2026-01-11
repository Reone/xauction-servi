package com.reone.xauction.service.impl

import com.reone.xauction.bean.dto.AuctionDto
import com.reone.xauction.bean.po.AuctionPo
import com.reone.xauction.bean.vo.AuctionVo
import com.reone.xauction.repository.AuctionRepository
import com.reone.xauction.service.AuctionService
import com.reone.xauction.util.currentTime
import com.reone.xauction.util.operatorId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.criteria.Predicate

/**
 *
 * @author reone
 * @date 2025/7/22 22:29
 */
@Service
class AuctionServiceImpl : AuctionService {

    @Autowired
    lateinit var auctionRepository: AuctionRepository
    override fun list(auctionDto: AuctionDto): List<AuctionVo> {
        //根据auctionDto中不为空的字段查询数据
        return auctionRepository.findAll { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            auctionDto.id?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("id"), it))
            }

            auctionDto.title?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("title"), it))
            }

            auctionDto.imgUrl?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("imgUrl"), it))
            }

            auctionDto.subTitle?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("subTitle"), it))
            }

            auctionDto.minPrice?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("minPrice"), it))
            }

            auctionDto.maxPrice?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("maxPrice"), it))
            }

            auctionDto.startTime?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("startTime"), it))
            }

            auctionDto.endTime?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("endTime"), it))
            }

            auctionDto.status?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("status"), it))
            }

            auctionDto.offerId?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("offerId"), it))
            }

            if (predicates.isNotEmpty()) {
                criteriaBuilder.and(*predicates.toTypedArray())
            } else {
                criteriaBuilder.conjunction() // 返回一个永真条件
            }
        }.map { AuctionVo(it) }
    }

    override fun info(id: Long): AuctionVo {
        val auction = auctionRepository.findByIdOrNull(id) ?: throw Throwable("拍卖不存在")
        return AuctionVo(auction)
    }

    override fun add(auction: AuctionDto): AuctionVo {
        val now = currentTime()
        val operator = operatorId
        val actionPo = AuctionPo().apply {
            this.title = auction.title
            this.imgUrl = auction.imgUrl
            this.imgUrls = auction.imgUrls?.toMutableList() ?: mutableListOf()
            if (this.imgUrl.isNullOrBlank()) {
                this.imgUrl = this.imgUrls.firstOrNull()
            }
            this.subTitle = auction.subTitle
            this.minPrice = auction.minPrice
            this.maxPrice = auction.maxPrice
            this.startTime = auction.startTime
            this.endTime = auction.endTime
            this.status = auction.status ?: 0
            this.offerId = auction.offerId
            this.createTime = now
            this.createBy = operator
            this.updateTime = now
            this.updateBy = operator
        }
        return AuctionVo(auctionRepository.save(actionPo))
    }

    override fun update(auction: AuctionDto): AuctionVo? {
        val auctionId = auction.id ?: throw Throwable("缺少id参数")
        val auctionPo = auctionRepository.findByIdOrNull(auctionId) ?: throw Throwable("拍卖不存在")
        val now = currentTime()
        val operator = operatorId
        auctionPo.apply {
            auction.title?.let { this.title = it }
            auction.imgUrl?.let { this.imgUrl = it }
            auction.imgUrls?.let { this.imgUrls = it.toMutableList() }
            if (this.imgUrl.isNullOrBlank()) {
                this.imgUrl = this.imgUrls.firstOrNull()
            }
            auction.subTitle?.let { this.subTitle = it }
            auction.minPrice?.let { this.minPrice = it }
            auction.maxPrice?.let { this.maxPrice = it }
            auction.startTime?.let { this.startTime = it }
            auction.endTime?.let { this.endTime = it }
            auction.status?.let { this.status = it }
            auction.offerId?.let { this.offerId = it }
            this.updateTime = now
            this.updateBy = operator
        }
        return AuctionVo(auctionRepository.save(auctionPo))
    }

    override fun delete(id: Long): Boolean {
        if (!auctionRepository.existsById(id)) {
            throw Throwable("拍卖不存在")
        }
        auctionRepository.deleteById(id)
        return true
    }
}
