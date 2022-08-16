package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV2
import mu.KotlinLogging
import java.sql.Connection
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
class MemberServiceV2(
    private val memberRepository: MemberRepositoryV2,
    private val dataSource: DataSource
) {

    fun accountTransfer(fromId: String, to: String, money: Int) {
        val con = dataSource.connection

        try {
            con.autoCommit = false
            // 비즈니스 로직
            bizLogic(con, fromId, to, money)
            con.commit() // 성공 시 커밋
        } catch (e: Exception) {
            con.rollback()
            throw IllegalStateException(e)
        } finally {
            release(con)
        }

    }

    private fun bizLogic(con: Connection, fromId: String, to: String, money: Int) {
        val fromMember = memberRepository.findById(con, fromId)
        val toMember = memberRepository.findById(con, to)

        memberRepository.update(con, fromId, fromMember.money.minus(money))
        validation(toMember)
        memberRepository.update(con, to, toMember.money.plus(money))
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
