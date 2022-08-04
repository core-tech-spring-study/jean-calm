package com.kakaopaysec.core.member.service

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member
import com.kakaopaysec.core.member.domain.MemoryMemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MemberServiceTest {

    lateinit var memberService: MemberService

    @BeforeEach
    fun beforeEach() {
        val appConfig = AppConfig()
        memberService = appConfig.memberService()
    }

    @Test
    fun join() {
        // given
        val member = Member(1, "memberA", Grade.VIP)

        // when
        memberService.join(member)

        // then
        val findMember = memberService.findMember(1)
        assertThat(member).isEqualTo(findMember)
    }
}
