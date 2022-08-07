package com.kakaopaysec.core.order.service

import com.kakaopaysec.core.annotation.MainDiscountPolicy
import com.kakaopaysec.core.discount.DiscountPolicy
import com.kakaopaysec.core.discount.RateDiscountPolicy
import com.kakaopaysec.core.member.domain.MemberRepository
import com.kakaopaysec.core.order.domain.Order
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class OrderServiceImpl(
    private val memberRepository: MemberRepository,
    @MainDiscountPolicy val discountPolicy: DiscountPolicy
) : OrderService {

    override fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order {
        val member = memberRepository.findById(memberId)

        val discountPrice = discountPolicy.discount(member!!, itemPrice)
        println("discountPrice: $discountPrice")

        return Order(memberId, itemName, itemPrice, discountPrice)
    }
}
