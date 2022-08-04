package com.kakaopaysec.core.member

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member

fun main() {
    val appConfig = AppConfig()
    val memberService = appConfig.memberService()
    val member = Member(1, "memberA", Grade.VIP)
    memberService.join(member)

    val findMember = memberService.findMember(1)
    println("new member = $member")
    println("findMember = $findMember")
}

