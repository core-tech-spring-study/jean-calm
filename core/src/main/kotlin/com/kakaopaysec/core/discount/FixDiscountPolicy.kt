package com.kakaopaysec.core.discount

import com.kakaopaysec.core.member.domain.Grade.VIP
import com.kakaopaysec.core.member.domain.Member

const val discountFixAmount = 1000

class FixDiscountPolicy: DiscountPolicy {


    override fun discount(member: Member, price: Int): Int {
        return when (member.grade) {
            VIP -> discountFixAmount
            else -> 0
        }
    }
}
