package com.kakaopaysec.jdbc.member.domain.repository

import com.kakaopaysec.jdbc.member.domain.Member
import java.sql.SQLException

interface MemberRepositoryEx {

    fun save(member: Member): Member

    fun findById(memberId: String): Member

    fun update(memberId: String, money: Int)

    fun delete(memberId: String)
}
