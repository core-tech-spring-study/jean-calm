package com.kakaopaysec.core.member.service

import com.kakaopaysec.core.member.domain.Member
import com.kakaopaysec.core.member.domain.MemberRepository

class MemberServiceImpl(
    private val memberRepository: MemberRepository
): MemberService {

    override fun join(member: Member) {
        memberRepository.save(member)
    }

    override fun findMember(memberId: Long): Member? {
        return memberRepository.findById(memberId)
    }
}
