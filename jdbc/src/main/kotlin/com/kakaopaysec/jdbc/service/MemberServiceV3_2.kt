package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV3
import mu.KotlinLogging
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.support.TransactionTemplate
import java.sql.Connection
import java.sql.SQLException

private val logger = KotlinLogging.logger {}

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */
class MemberServiceV3_2(
    private val memberRepository: MemberRepositoryV3,
    private val transactionManager: PlatformTransactionManager
) {

    lateinit var txTemplate: TransactionTemplate

    init {
        txTemplate = TransactionTemplate(transactionManager)
    }

    fun accountTransfer(fromId: String, to: String, money: Int) {
        // 비즈니스 로직
        txTemplate.executeWithoutResult {
            try {
                bizLogic(fromId, to, money)
            } catch (e : SQLException) {
                throw IllegalStateException(e)
            }
        }
    }

    private fun bizLogic(fromId: String, to: String, money: Int) {
        val fromMember = memberRepository.findById(fromId)
        val toMember = memberRepository.findById(to)

        memberRepository.update(fromId, fromMember.money.minus(money))
        validation(toMember)
        memberRepository.update(to, toMember.money.plus(money))
    }

    private fun validation(toMember: Member) {
        if (toMember.memberId == "ex") {
            throw IllegalStateException("이체중 예외 발생")
        }
    }
}
