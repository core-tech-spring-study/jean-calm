package com.kakaopaysec.core.member.domain

interface MemberRepository {

    fun save(member: Member)

    fun findById(memberId: Long): Member?
}
