package com.kakaopaysec.core.singleton

import com.kakaopaysec.core.AppConfig
import com.kakaopaysec.core.member.service.MemberService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    fun pureContainer() {
        val appConfig = AppConfig()

        // 1. 조회: 호출할 때 마다 객체를 생성
        val memberService1 = appConfig.memberService()

        // 2. 조회: 호출할 때 마다 객체를 생성
        val memberService2 = appConfig.memberService()

        // 참조값이 다른것을 확인
        println("memberService1 = $memberService1")
        println("memberService2 = $memberService2")

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2)
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    fun singletonServiceTest() {
        val singletonService1 = SingletonService.getInstance()
        val singletonService2 = SingletonService.getInstance()
        assertThat(singletonService1).isSameAs(singletonService2)
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    fun springContainer() {
        val ac = AnnotationConfigApplicationContext(AppConfig::class.java)
        val memberService1 = ac.getBean("memberService", MemberService::class.java)
        val memberService2 = ac.getBean("memberService", MemberService::class.java)

        assertThat(memberService1).isSameAs(memberService2)
    }
}
