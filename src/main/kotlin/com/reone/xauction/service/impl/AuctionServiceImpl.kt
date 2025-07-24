package com.reone.xauction.service.impl

import com.reone.xauction.bean.dto.AuctionDto
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
            var predicate: Predicate? = null
            if (auctionDto.id != null) {
                predicate = criteriaBuilder.equal(root.get<Any>("id"), auctionDto.id)
            }
            if (auctionDto.title != null) {
                predicate =
                    criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("title"), auctionDto.title))
            }
            if (auctionDto.imgUrl != null) {
                predicate =
                    criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("imgUrl"), auctionDto.imgUrl))
            }
            if (auctionDto.subTitle != null) {
                predicate =
                    criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("desc"), auctionDto.subTitle))
            }
            if (auctionDto.minPrice != null) {
                predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(root.get<Any>("minPrice"), auctionDto.minPrice)
                )
            }
            if (auctionDto.maxPrice != null) {
                predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(root.get<Any>("maxPrice"), auctionDto.maxPrice)
                )
            }
            if (auctionDto.startTime != null) {
                predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(root.get<Any>("startTime"), auctionDto.startTime)
                )
            }
            if (auctionDto.endTime != null) {
                predicate =
                    criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("endTime"), auctionDto.endTime))
            }
            if (auctionDto.status != null) {
                predicate =
                    criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("status"), auctionDto.status))
            }
            predicate
        }.map { AuctionVo(it) }
    }

    override fun info(id: Long): AuctionVo {
        return AuctionVo(auctionRepository.findById(id).get())
    }

    override fun add(auction: AuctionDto): Boolean {
        return try {
            auction.createTime = currentTime()
            auction.createBy = currentUserId.toString()
            auction.updateTime = currentTime()
            auction.updateBy = currentUserId.toString()
            auctionRepository.save(auction)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun update(auction: AuctionDto): Boolean {
        return try {
            auction.updateTime = currentTime()
            auction.updateBy = currentUserId.toString()
            auctionRepository.save(auction)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(id: Long): Boolean {
        return try {
            auctionRepository.deleteById(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}