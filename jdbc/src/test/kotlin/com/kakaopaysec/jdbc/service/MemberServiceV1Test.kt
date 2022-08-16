package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.connection.password
import com.kakaopaysec.jdbc.connection.url
import com.kakaopaysec.jdbc.connection.userName
import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV1
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource

const val MEMBER_A = "memberA"
const val MEMBER_B = "memberB"
const val MEMBER_EX = "ex"

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */

internal class MemberServiceV1Test {

    lateinit var memberRepository: MemberRepositoryV1
    lateinit var memberService: MemberServiceV1

    @BeforeEach
    fun setUp() {
        val dataSource = DriverManagerDataSource(url, userName, password)
        memberRepository = MemberRepositoryV1(dataSource)
        memberService = MemberServiceV1(memberRepository)
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
        memberRepository.save(memberA)
        memberRepository.save(memberB)

        // when
        memberService.accountTransfer(memberA.memberId, memberB.memberId, 2000)

        // then
        val findMemberA = memberRepository.findById(memberA.memberId)
        val findMemberB = memberRepository.findById(memberB.memberId)

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
        assertThatThrownBy {
            memberService.accountTransfer(memberA.memberId, memberEx.memberId, 2000)
        }.isInstanceOf(IllegalStateException::class.java)

        // then
        val findMemberA = memberRepository.findById(memberA.memberId)
        val findMemberB = memberRepository.findById(memberEx.memberId)

        assertThat(findMemberA.money).isEqualTo(8000)
        assertThat(findMemberB.money).isEqualTo(10000)
    }
}
