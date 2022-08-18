package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV3
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV5
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}


/**
 * 트랜잭션 - DataSource, transactionManager 자동등록
 */
@SpringBootTest
internal class MemberServiceV4Test {

    @Autowired
    lateinit var memberRepository: MemberRepositoryV5

    @Autowired
    lateinit var memberService: MemberServiceV4

    @TestConfiguration
    class TestConfig {

        @Bean
        fun memberRepositoryV5(dataSource: DataSource): MemberRepositoryV5 {
            return MemberRepositoryV5(dataSource)
        }

        @Bean
        fun memberServiceV4(dataSource: DataSource): MemberServiceV4 {
            return MemberServiceV4(memberRepositoryV5(dataSource))
        }
    }

    @AfterEach
    fun after() {
        memberRepository.delete(MEMBER_A)
        memberRepository.delete(MEMBER_B)
        memberRepository.delete(MEMBER_EX)
    }


    @Test
    @DisplayName("정상 이체")
    fun accountTransfer() {
        // given
        val memberA = Member(MEMBER_A, 10000)
        val memberB = Member(MEMBER_B, 10000)

        // when
        memberRepository.save(memberA)
        memberRepository.save(memberB)

        logger.info { "START TX" }
        memberService.accountTransfer(memberA.memberId, memberB.memberId, 2000)
        logger.info { "END TX" }

        val findMemberA = memberRepository.findById(memberA.memberId)
        val findMemberB = memberRepository.findById(memberB.memberId)

        // then
        Assertions.assertThat(findMemberA?.money).isEqualTo(8000)
        Assertions.assertThat(findMemberB?.money).isEqualTo(12000)
    }

    @Test
    @DisplayName("이체 중 예외발생")
    fun accountTransferEx() {
        // given
        val memberA = Member(MEMBER_A, 10000)
        val memberEx = Member(MEMBER_EX, 10000)
        memberRepository.save(memberA)
        memberRepository.save(memberEx)

        // when
        Assertions.assertThatThrownBy {
            memberService.accountTransfer(memberA.memberId, memberEx.memberId, 2000)
        }.isInstanceOf(IllegalStateException::class.java)

        // then
        val findMemberA = memberRepository.findById(memberA.memberId)
        val findMemberB = memberRepository.findById(memberEx.memberId)

        Assertions.assertThat(findMemberA?.money).isEqualTo(10000)
        Assertions.assertThat(findMemberB?.money).isEqualTo(10000)
    }
}
