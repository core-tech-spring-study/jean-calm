package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.connection.password
import com.kakaopaysec.jdbc.connection.url
import com.kakaopaysec.jdbc.connection.userName
import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV3
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * 트랜잭션 -트랜잭션 AOP
 */
@SpringBootTest
internal class MemberServiceV3_3Test {

    @Autowired
    lateinit var memberRepository: MemberRepositoryV3

    @Autowired
    lateinit var memberService: MemberServiceV3_3

    @TestConfiguration
    class TestConfig {

        @Bean
        fun dataSource(): DataSource {
            return DriverManagerDataSource(url, userName, password)
        }

        @Bean
        fun transactionManager(): PlatformTransactionManager {
            return DataSourceTransactionManager(dataSource())
        }

        @Bean
        fun memberRepositoryV3(): MemberRepositoryV3 {
            return MemberRepositoryV3(dataSource())
        }

        @Bean
        fun memberServiceV3_3(): MemberServiceV3_3 {
            return MemberServiceV3_3(memberRepositoryV3())
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
        Assertions.assertThat(findMemberA.money).isEqualTo(8000)
        Assertions.assertThat(findMemberB.money).isEqualTo(12000)
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

        Assertions.assertThat(findMemberA.money).isEqualTo(10000)
        Assertions.assertThat(findMemberB.money).isEqualTo(10000)
    }
}
