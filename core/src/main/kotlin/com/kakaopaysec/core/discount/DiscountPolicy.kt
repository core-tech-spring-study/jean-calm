package com.kakaopaysec.core.discount

import com.kakaopaysec.core.member.domain.Member

interface DiscountPolicy {
    /**
     * @return 할인대상 금액
     */
    fun discount(member: Member, price: Int): Int
}
