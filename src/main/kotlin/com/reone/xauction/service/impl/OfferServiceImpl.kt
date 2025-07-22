package com.reone.xauction.service.impl

import com.reone.xauction.bean.dto.OfferDto
import com.reone.xauction.bean.vo.OfferVo
import com.reone.xauction.repository.OfferRepository
import com.reone.xauction.service.OfferService
import com.reone.xauction.service.UserService
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
class OfferServiceImpl : OfferService {

    @Autowired
    lateinit var offerRepository: OfferRepository

    @Autowired
    lateinit var userService: UserService
    override fun list(auctionDto: OfferDto): List<OfferVo> {
        //根据auctionDto中不为空的字段查询数据
        return offerRepository.findAll { root, query, criteriaBuilder ->
            var predicate: Predicate? = null
            if (auctionDto.id != null) {
                predicate = criteriaBuilder.equal(root.get<Any>("id"), auctionDto.id)
            }
            if (auctionDto.auctionId != null) {
                predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(root.get<Any>("auctionId"), auctionDto.auctionId)
                )
            }
            if (auctionDto.userId != null) {
                predicate =
                    criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("userId"), auctionDto.userId))
            }
            if (auctionDto.price != null) {
                predicate =
                    criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("price"), auctionDto.price))
            }
            if (auctionDto.win != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get<Any>("win"), auctionDto.win))
            }
            predicate
        }.map { pojo ->
            val offer = OfferVo(pojo)
            pojo.userId?.let {
                offer.user = userService.findById(it)
            }
            return@map offer
        }
    }

    override fun info(id: Long): OfferVo {
        val pojo = offerRepository.findById(id).get()
        val offer = OfferVo(pojo)
        pojo.userId?.let {
            offer.user = userService.findById(it)
        }
        return offer
    }

    override fun add(offer: OfferDto): Boolean {
        return try {
            offer.createTime = currentTime()
            offer.createBy = currentUserId.toString()
            offer.updateTime = currentTime()
            offer.updateBy = currentUserId.toString()
            offerRepository.save(offer)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun update(offer: OfferDto): Boolean {
        return try {
            offer.updateTime = currentTime()
            offer.updateBy = currentUserId.toString()
            offerRepository.save(offer)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(id: Long): Boolean {
        return try {
            offerRepository.deleteById(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}