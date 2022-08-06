package com.kakaopaysec.core.beanfind

import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member
import com.kakaopaysec.core.member.domain.MemberRepository
import com.kakaopaysec.core.member.domain.MemoryMemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


internal class ApplicationContextSameBeanFindTest {

    lateinit var ac: AnnotationConfigApplicationContext

    @BeforeEach
    fun setUp() {
        ac = AnnotationConfigApplicationContext(SameBeanConfig::class.java)
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
    fun findBeanByTypeDuplicate() {
        assertThrows(NoSuchBeanDefinitionException::class.java) {
           ac.getBean(MemberRepository::class.java)
        }
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름으로 지정하면 됩니다.")
    fun findBeanByName() {
        val memberRepository = ac.getBean("memberRepository1", MemberRepository::class.java)
        assertThat(memberRepository).isInstanceOf(MemberRepository::class.java)
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    fun findAllBeanType() {
        val beansOfType = ac.getBeansOfType(MemberRepository::class.java)

        beansOfType.forEach {
            println("key = ${it.key}, value = ${it.value}")
        }

        assertThat(beansOfType.size).isEqualTo(2)
    }

    @Configuration
    class SameBeanConfig {

        @Bean
        fun memberRepository1(): MemberRepository {
            val memberRepository = MemoryMemberRepository()
            memberRepository.save(Member(2L, "tommy", Grade.VIP))
            return MemoryMemberRepository()
        }

        @Bean
        fun memberRepository2(): MemberRepository {
            val memberRepository = MemoryMemberRepository()
            memberRepository.save(Member(1L, "jean", Grade.BASIC))
            return MemoryMemberRepository()
        }
    }
}
