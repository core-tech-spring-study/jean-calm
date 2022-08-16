package com.kakaopaysec.jdbc.service

import com.kakaopaysec.jdbc.member.domain.Member
import com.kakaopaysec.jdbc.member.domain.repository.MemberRepositoryV1

class MemberServiceV1(
    private val memberRepositoryV1: MemberRepositoryV1
) {

    fun accountTransfer(fromId: String, to: String, money: Int) {

        val fromMember = memberRepositoryV1.findById(fromId)
        val toMember = memberRepositoryV1.findById(to)

        memberRepositoryV1.update(fromId, fromMember.money.minus(money))
        validation(toMember)
        memberRepositoryV1.update(to, toMember.money.plus(money))
    }

    private fun validation(toMember: Member) {
        if (toMember.memberId == "ex") {
            throw IllegalStateException("이체중 예외 발생")
        }
    }
}
