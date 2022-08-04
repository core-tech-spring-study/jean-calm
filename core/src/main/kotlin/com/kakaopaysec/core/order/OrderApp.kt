package com.kakaopaysec.core.order

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.discount.FixDiscountPolicy
import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member
import com.kakaopaysec.core.member.domain.MemoryMemberRepository
import com.kakaopaysec.core.member.service.MemberServiceImpl
import com.kakaopaysec.core.order.service.OrderServiceImpl

fun main() {

    val appConfig = AppConfig()

    val memberService = appConfig.memberService()
    val orderService = appConfig.orderService()

    val memberId = 1L
    val member = Member(memberId, "memberA", Grade.VIP)
    memberService.join(member)

    val order = orderService.createOrder(memberId, "itemA", 10000)
    println("order = $order")
    println("order = ${order.calculatePrice()}")
}
