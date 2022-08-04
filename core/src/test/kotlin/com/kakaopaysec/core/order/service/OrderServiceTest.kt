package com.kakaopaysec.core.order.service

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.discount.FixDiscountPolicy
import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member
import com.kakaopaysec.core.member.domain.MemoryMemberRepository
import com.kakaopaysec.core.member.service.MemberService
import com.kakaopaysec.core.member.service.MemberServiceImpl
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderServiceTest {

    lateinit var memberService: MemberService
    lateinit var orderService: OrderService

    @BeforeEach
    fun beforeEach() {
        val appConfig = AppConfig()
        memberService = appConfig.memberService()
        orderService = appConfig.orderService()
    }

    @Test
    fun createOrder() {
        // given
        val memberId = 1L
        val member = Member(memberId, "memberA", Grade.VIP)
        memberService.join(member)

        // when
        val order = orderService.createOrder(memberId, "itemA", 10000)

        // then
        assertThat(order.discountPrice).isEqualTo(1000)
    }
}
