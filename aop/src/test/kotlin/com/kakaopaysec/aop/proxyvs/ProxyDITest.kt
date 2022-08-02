package com.kakaopaysec.aop.proxyvs

import com.kakaopaysec.aop.member.service.MemberService
import com.kakaopaysec.aop.member.service.MemberServiceImpl
import com.kakaopaysec.aop.proxyvs.code.ProxyDIAspect
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

private val logger = KotlinLogging.logger {}

@Import(ProxyDIAspect::class)
//@SpringBootTest(properties = ["spring.aop.proxy-target-class=false"]) // JDK 동적프록시
//@SpringBootTest(properties = ["spring.aop.proxy-target-class=true"]) // CGLIB 프록시
@SpringBootTest
class ProxyDITest {

    @Autowired lateinit var memberService: MemberService

    @Autowired lateinit var memberServiceImpl: MemberServiceImpl

    @Test
    fun go() {
        logger.info { "memberService class = {${memberService.javaClass}}" }
        logger.info { "memberServiceImpl class = {${memberServiceImpl.javaClass}}" }
        memberServiceImpl.hello("hello")
    }
}
