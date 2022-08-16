package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.connection.password
import com.kakaopaysec.jdbc.connection.url
import com.kakaopaysec.jdbc.connection.userName
import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV1
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV2
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource

private val logger = KotlinLogging.logger {}


/**
 * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
 */
internal class MemberServiceV2Test {

    lateinit var memberRepository: MemberRepositoryV2
    lateinit var memberService: MemberServiceV2

    @BeforeEach
    fun setUp() {
        val dataSource = DriverManagerDataSource(url, userName, password)
        memberRepository = MemberRepositoryV2(dataSource)
        memberService = MemberServiceV2(memberRepository, dataSource)
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
        assertThat(findMemberA.money).isEqualTo(8000)
        assertThat(findMemberB.money).isEqualTo(12000)
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

        assertThat(findMemberA.money).isEqualTo(10000)
        assertThat(findMemberB.money).isEqualTo(10000)
    }
}
