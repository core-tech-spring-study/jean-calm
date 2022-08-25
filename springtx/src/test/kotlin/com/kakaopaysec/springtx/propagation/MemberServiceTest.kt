package com.kakaopaysec.springtx.propagation

import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.springframework.transaction.UnexpectedRollbackException

private val logger = KotlinLogging.logger {}

@SpringBootTest
internal class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var logRepository: LogRepository

    /**
     *  memberService    @Transactional: OFF
     *  memberRepository @Transactional: ON
     *  logRepository    @Transactional: ON
     */
    @Test
    fun outerTxOff_success() {
        // given
        val username = "outerTxOff_success"

        // when
        memberService.joinV1(username)

        // then
        assertTrue(memberRepository.find(username).isPresent)
        assertTrue(logRepository.find(username).isPresent)
    }

    /**
     *  memberService    @Transactional: OFF
     *  memberRepository @Transactional: ON
     *  logRepository    @Transactional: ON RuntimeException
     */
    @Test
    fun outerTxOff_fail() {
        // given
        val username = "로그예외_outerTxOff_fail"

        // when
        assertThatThrownBy {
            memberService.joinV1(username)
        }.isInstanceOf(RuntimeException::class.java)

        // then
        assertTrue(memberRepository.find(username).isPresent)
        assertTrue(logRepository.find(username).isEmpty)
    }


    /**
     *  memberService    @Transactional: ON
     *  memberRepository @Transactional: OFF
     *  logRepository    @Transactional: OFF
     */
    @Test
    fun singleTx() {
        // given
        val username = "outerTxOff_success"

        // when
        memberService.joinV1(username)

        // then
        assertTrue(memberRepository.find(username).isPresent)
        assertTrue(logRepository.find(username).isPresent)
    }

    /**
     *  memberService    @Transactional: ON
     *  memberRepository @Transactional: ON
     *  logRepository    @Transactional: ON
     */
    @Test
    fun outerTxOn_success() {
        // given
        val username = "outerTxOn_success"

        // when
        memberService.joinV1(username)

        // then
        assertTrue(memberRepository.find(username).isPresent)
        assertTrue(logRepository.find(username).isPresent)
    }

    /**
     *  memberService    @Transactional: ON
     *  memberRepository @Transactional: ON
     *  logRepository    @Transactional: ON
     */
    @Test
    fun outerTxOn_fail() {
        // given
        val username = "로그예외_outerTxOn_fail"

        // when
        assertThatThrownBy {
            memberService.joinV1(username)
        }.isInstanceOf(RuntimeException::class.java)

        // then
        assertTrue(memberRepository.find(username).isEmpty)
        assertTrue(logRepository.find(username).isEmpty)
    }

    /**
     *  memberService    @Transactional: ON
     *  memberRepository @Transactional: ON
     *  logRepository    @Transactional: ON
     */
    @Test
    fun recoverException_fail() {
        // given
        val username = "로그예외_recoverException_fail"

        // when
        assertThatThrownBy {
            memberService.joinV2(username)
        }.isInstanceOf(UnexpectedRollbackException::class.java)

        // then
        assertTrue(memberRepository.find(username).isEmpty)
        assertTrue(logRepository.find(username).isEmpty)
    }

    /**
     *  memberService    @Transactional: ON
     *  memberRepository @Transactional: ON
     *  logRepository    @Transactional: ON(REQUIRES_NEW) Exception
     */
    @Test
    fun recoverException_success() {
        // given
        val username = "로그예외_recoverException_success"

        // when
        memberService.joinV2(username)

        // then member 저장, log 롤백
        assertTrue(memberRepository.find(username).isPresent)
        assertTrue(logRepository.find(username).isEmpty)
    }
}
