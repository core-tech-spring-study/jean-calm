package com.kakaopaysec.core.member

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member
import com.kakaopaysec.core.member.service.MemberService
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {

    val applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
    val memberService = applicationContext.getBean("memberService", MemberService::class.java)

    val member = Member(1, "memberA", Grade.VIP)
    memberService.join(member)

    val findMember = memberService.findMember(1)
    println("new member = $member")
    println("findMember = $findMember")
}

