package com.kakaopaysec.core.member.service

import com.kakaopaysec.core.member.domain.Member

interface MemberService {

    fun join(member: Member)

    fun findMember(memberId: Long): Member?
}
