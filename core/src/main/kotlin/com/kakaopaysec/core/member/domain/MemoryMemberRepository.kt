package com.kakaopaysec.core.member.domain

import org.springframework.stereotype.Component


@Component
class MemoryMemberRepository: MemberRepository {

    override fun save(member: Member) {
        store.apply {
            put(member.id, member)
        }
    }

    override fun findById(memberId: Long): Member? {
        return store[memberId]
    }

    companion object {
        private val store: MutableMap<Long, Member> = hashMapOf()
    }
}
