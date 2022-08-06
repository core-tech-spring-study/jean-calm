package com.kakaopaysec.core.order

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member
import com.kakaopaysec.core.member.service.MemberService
import com.kakaopaysec.core.order.service.OrderService
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
    val memberService = applicationContext.getBean("memberService", MemberService::class.java)
    val orderService = applicationContext.getBean("orderService", OrderService::class.java)

    val memberId = 1L
    val member = Member(memberId, "memberA", Grade.VIP)
    memberService.join(member)

    val order = orderService.createOrder(memberId, "itemA", 20000)
    println("order = $order")
    println("order = ${order.calculatePrice()}")
}
