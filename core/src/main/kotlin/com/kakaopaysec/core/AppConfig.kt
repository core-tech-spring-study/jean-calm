package com.kakaopaysec.core

import com.kakaopaysec.core.discount.FixDiscountPolicy
import com.kakaopaysec.core.member.domain.MemoryMemberRepository
import com.kakaopaysec.core.member.service.MemberService
import com.kakaopaysec.core.member.service.MemberServiceImpl
import com.kakaopaysec.core.order.service.OrderService
import com.kakaopaysec.core.order.service.OrderServiceImpl

class AppConfig {

    fun memberService(): MemberService {
        return MemberServiceImpl(MemoryMemberRepository())
    }

    fun orderService(): OrderService {
        return OrderServiceImpl(MemoryMemberRepository(), FixDiscountPolicy())
    }
}
