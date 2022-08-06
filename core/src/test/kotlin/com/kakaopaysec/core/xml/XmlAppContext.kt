package com.kakaopaysec.core.xml

import com.kakaopaysec.core.member.service.MemberService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.support.GenericXmlApplicationContext

internal class XmlAppContext {

    @Test
    fun xmlAppContext() {
        val ac = GenericXmlApplicationContext("appConfig.xml")
        val memberService = ac.getBean("memberService", MemberService::class.java)
        assertThat(memberService).isInstanceOf(MemberService::class.java)
    }
}
