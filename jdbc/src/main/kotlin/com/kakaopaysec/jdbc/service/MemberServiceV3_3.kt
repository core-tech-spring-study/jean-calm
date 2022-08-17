package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV3
import mu.KotlinLogging
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

open class MemberServiceV3_3(
    private val memberRepository: MemberRepositoryV3
) {

    @Transactional
    open fun accountTransfer(fromId: String, to: String, money: Int) {
        // 비즈니스 로직
        bizLogic(fromId, to, money)
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


