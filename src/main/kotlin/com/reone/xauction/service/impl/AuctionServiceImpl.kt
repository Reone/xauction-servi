package com.reone.xauction.service.impl

import com.reone.xauction.bean.dto.AuctionDto
import com.reone.xauction.bean.po.AuctionPo
import com.reone.xauction.bean.vo.AuctionVo
import com.reone.xauction.repository.AuctionRepository
import com.reone.xauction.service.AuctionService
import com.reone.xauction.util.currentTime
import com.reone.xauction.util.currentUserId
import org.springframework.beans.factory.annotation.Autowired
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
        return auctionRepository.findAll { root, query, criteriaBuilder ->
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
                predicates.add(criteriaBuilder.equal(root.get<Any>("desc"), it))
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

            if (predicates.isNotEmpty()) {
                criteriaBuilder.and(*predicates.toTypedArray())
            } else {
                criteriaBuilder.conjunction() // 返回一个永真条件
            }
        }.map { AuctionVo(it) }
    }

    override fun info(id: Long): AuctionVo {
        return AuctionVo(auctionRepository.findById(id).get())
    }

    override fun add(auction: AuctionDto): AuctionVo {
        auction.createTime = currentTime()
        auction.createBy = currentUserId.toString()
        auction.updateTime = currentTime()
        auction.updateBy = currentUserId.toString()
        val actionPo = AuctionPo()
        actionPo.apply {
            this.title = auction.title
            this.imgUrl = auction.imgUrl
            this.subTitle = auction.subTitle
            this.minPrice = auction.minPrice
            this.maxPrice = auction.maxPrice
            this.startTime = auction.startTime
            this.endTime = auction.endTime
        }
        return AuctionVo(auctionRepository.save(actionPo))
    }

    override fun update(auction: AuctionDto): AuctionVo? {
        auction.updateTime = currentTime()
        auction.updateBy = currentUserId.toString()
        val auctionPo = auctionRepository.findById(auction.id!!).get()
        auctionPo.apply {
            if (auction.title != null) {
                this.title = auction.title
            }
            if (auction.imgUrl != null) {
                this.imgUrl = auction.imgUrl
            }
            if (auction.subTitle != null) {
                this.subTitle = auction.subTitle
            }
            if (auction.minPrice != null) {
                this.minPrice = auction.minPrice
            }
            if (auction.maxPrice != null) {
                this.maxPrice = auction.maxPrice
            }
            if (auction.startTime != null) {
                this.startTime = auction.startTime
            }
            if (auction.endTime != null) {
                this.endTime = auction.endTime
            }
            if (auction.status != null) {
                this.status = auction.status
            }
            if (auction.offerId != null) {
                this.offerId = auction.offerId
            }
            this.updateTime = currentTime()
            this.updateBy = currentUserId.toString()
        }
        return AuctionVo(auctionRepository.save(auctionPo))
    }

    override fun delete(id: Long): Boolean {
        auctionRepository.deleteById(id)
        return true
    }
}