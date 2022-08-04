package com.kakaopaysec.core.discount

import com.kakaopaysec.core.member.domain.Grade.*
import com.kakaopaysec.core.member.domain.Member

const val discountPercent = 10

class RateDiscountPolicy: DiscountPolicy {

    override fun discount(member: Member, price: Int): Int {
        return when(member.grade) {
            VIP -> price * discountPercent / 100
            else -> 0
        }
    }
}