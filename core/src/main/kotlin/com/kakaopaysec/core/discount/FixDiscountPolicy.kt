package com.kakaopaysec.core.discount

import com.kakaopaysec.core.member.domain.Grade.VIP
import com.kakaopaysec.core.member.domain.Member
import org.springframework.stereotype.Component

const val discountFixAmount = 1000

@Component
class FixDiscountPolicy: DiscountPolicy {


    override fun discount(member: Member, price: Int): Int {
        return when (member.grade) {
            VIP -> discountFixAmount
            else -> 0
        }
    }
}
