package com.kakaopaysec.core.scan

import com.kakaopaysec.core.AutoAppConfig
import com.kakaopaysec.core.member.service.MemberService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class AutoAppConfigTest {

    @Test
    fun basicScan() {
        val ac = AnnotationConfigApplicationContext(AutoAppConfig::class.java)
        val memberService = ac.getBean(MemberService::class.java)
        assertThat(memberService).isInstanceOf(MemberService::class.java)
    }
}
