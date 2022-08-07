package com.kakaopaysec.core.discount

import com.kakaopaysec.core.annotation.MainDiscountPolicy
import com.kakaopaysec.core.member.domain.Grade.VIP
import com.kakaopaysec.core.member.domain.Member
import org.springframework.stereotype.Component

const val discountPercent = 10
@Component
@MainDiscountPolicy
class RateDiscountPolicy: DiscountPolicy {

    override fun discount(member: Member, price: Int): Int {
        return when(member.grade) {
            VIP -> price * discountPercent / 100
            else -> 0
        }
    }
}
