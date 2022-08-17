package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV2
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV3
import mu.KotlinLogging
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.sql.Connection
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
class MemberServiceV3_1(
    private val memberRepository: MemberRepositoryV3,
    private val transactionManager: PlatformTransactionManager
) {

    fun accountTransfer(fromId: String, to: String, money: Int) {

        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            // 비즈니스 로직
            bizLogic(fromId, to, money)
            transactionManager.commit(status) // 성공시 커밋
        } catch (e: Exception) {
            transactionManager.rollback(status) // 실패시 롤백
            throw IllegalStateException(e)
        }
    }

    private fun bizLogic(fromId: String, to: String, money: Int) {
        val fromMember = memberRepository.findById(fromId)
        val toMember = memberRepository.findById(to)

        memberRepository.update(fromId, fromMember.money.minus(money))
        validation(toMember)
        memberRepository.update(to, toMember.money.plus(money))
    }

    private fun release(con: Connection) {
        if (con != null) {
            try {
                con.autoCommit = true // 커넥션 풀 고려
            } catch (e: Exception) {
                logger.error { "error = $e" }
            }
        }
    }

    private fun validation(toMember: Member) {
        if (toMember.memberId == "ex") {
            throw IllegalStateException("이체중 예외 발생")
        }
    }
}
