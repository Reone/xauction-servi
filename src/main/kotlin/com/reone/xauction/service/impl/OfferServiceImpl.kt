package com.reone.xauction.service.impl

import com.reone.xauction.bean.dto.OfferDto
import com.reone.xauction.bean.po.OfferPo
import com.reone.xauction.bean.vo.OfferVo
import com.reone.xauction.repository.OfferRepository
import com.reone.xauction.service.OfferService
import com.reone.xauction.service.UserService
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
class OfferServiceImpl : OfferService {

    @Autowired
    lateinit var offerRepository: OfferRepository

    @Autowired
    lateinit var userService: UserService
    override fun list(offerDto: OfferDto): List<OfferVo> {
        //根据offerDto中不为空的字段查询数据
        return offerRepository.findAll { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            offerDto.id?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("id"), it))
            }
            offerDto.auctionId?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("auctionId"), it))
            }
            offerDto.userId?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("userId"), it))
            }
            offerDto.price?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("price"), it))
            }
            offerDto.win?.let {
                predicates.add(criteriaBuilder.equal(root.get<Any>("win"), it))
            }
            if (predicates.isNotEmpty()) {
                criteriaBuilder.and(*predicates.toTypedArray())
            } else {
                criteriaBuilder.conjunction()
            }
        }.map { it.toVoWithUser() }
    }

    override fun info(id: Long): OfferVo {
        val pojo = offerRepository.findByIdOrNull(id) ?: throw Throwable("出价不存在")
        return pojo.toVoWithUser()
    }

    override fun add(offer: OfferDto): OfferVo {
        val now = currentTime()
        val operator = operatorId
        val po = OfferPo().apply {
            this.auctionId = offer.auctionId ?: throw Throwable("缺少拍卖id")
            this.userId = offer.userId ?: throw Throwable("缺少用户id")
            this.price = offer.price
            this.win = offer.win ?: 0
            this.createTime = now
            this.createBy = operator
            this.updateTime = now
            this.updateBy = operator
        }
        val saved = offerRepository.save(po)
        return saved.toVoWithUser()
    }

    override fun update(offer: OfferDto): OfferVo {
        val offerId = offer.id ?: throw Throwable("缺少id参数")
        val po = offerRepository.findByIdOrNull(offerId) ?: throw Throwable("出价不存在")
        val now = currentTime()
        po.apply {
            offer.auctionId?.let { this.auctionId = it }
            offer.userId?.let { this.userId = it }
            offer.price?.let { this.price = it }
            offer.win?.let { this.win = it }
            this.updateTime = now
            this.updateBy = operatorId
        }
        val saved = offerRepository.save(po)
        return saved.toVoWithUser()
    }

    override fun delete(id: Long): Boolean {
        if (!offerRepository.existsById(id)) {
            throw Throwable("出价不存在")
        }
        offerRepository.deleteById(id)
        return true
    }

    private fun OfferPo.toVoWithUser(): OfferVo {
        val vo = OfferVo(this)
        this.userId?.let {
            vo.user = userService.findById(it)
        }
        return vo
    }
}
