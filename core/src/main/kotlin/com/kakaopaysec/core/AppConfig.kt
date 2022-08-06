package com.kakaopaysec.core

import com.kakaopaysec.core.discount.DiscountPolicy
import com.kakaopaysec.core.discount.RateDiscountPolicy
import com.kakaopaysec.core.member.domain.MemberRepository
import com.kakaopaysec.core.member.domain.MemoryMemberRepository
import com.kakaopaysec.core.member.service.MemberService
import com.kakaopaysec.core.member.service.MemberServiceImpl
import com.kakaopaysec.core.order.service.OrderService
import com.kakaopaysec.core.order.service.OrderServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun memberService(): MemberService {
        return MemberServiceImpl(MemoryMemberRepository())
    }

    @Bean
    fun memberRepository(): MemberRepository {
        return MemoryMemberRepository()
    }

    @Bean
    fun orderService(): OrderService {
        return OrderServiceImpl(memberRepository(), discountPolicy())
    }
    @Bean
    fun discountPolicy(): DiscountPolicy {
        //return FixDiscountPolicy()
        return RateDiscountPolicy()
    }
}
