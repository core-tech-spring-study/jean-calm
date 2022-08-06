package com.kakaopaysec.core.beanfind

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.member.service.MemberService
import com.kakaopaysec.core.member.service.MemberServiceImpl
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.NoSuchBeanDefinitionException

internal class ApplicationContextBasicTest {

    lateinit var ac: AnnotationConfigApplicationContext

    @BeforeEach
    fun setUp() {
        ac = AnnotationConfigApplicationContext(AppConfig::class.java)
    }

    @Test
    @DisplayName("빈 이름으로 조회")
    fun findBeanByName() {
        val memberService = ac.getBean("memberService", MemberService::class.java)
        assertThat(memberService).isInstanceOf(MemberServiceImpl::class.java)
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    fun findBeanByType() {
        val memberService = ac.getBean(MemberService::class.java)
        assertThat(memberService).isInstanceOf(MemberServiceImpl::class.java)
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    fun findBeanByName2() {
        val memberService = ac.getBean("memberService", MemberServiceImpl::class.java)
        assertThat(memberService).isInstanceOf(MemberServiceImpl::class.java)
    }

    @Test
    @DisplayName("빈 이름으로 조회 X")
    fun findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException::class.java) {
            val memberService = ac.getBean("xxx", MemberService::class.java)
        }
    }
}
