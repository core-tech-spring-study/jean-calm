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
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource

private val logger = KotlinLogging.logger {}

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */

internal class MemberServiceV3_2Test {

    lateinit var memberRepository: MemberRepositoryV3
    lateinit var memberService: MemberServiceV3_2

    @BeforeEach
    fun setUp() {
        val dataSource = DriverManagerDataSource(url, userName, password)
        memberRepository = MemberRepositoryV3(dataSource)
        val transactionManager = DataSourceTransactionManager(dataSource)
        memberService = MemberServiceV3_2(memberRepository, transactionManager)
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
