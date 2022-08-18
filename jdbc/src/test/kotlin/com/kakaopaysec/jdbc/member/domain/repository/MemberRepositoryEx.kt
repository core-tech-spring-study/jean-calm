package com.kakaopaysec.jdbc.member.domain.repository

import com.kakaopaysec.jdbc.member.domain.Member
import java.sql.SQLException




interface MemberRepositoryEx {

    @Throws(SQLException::class)
    fun save(member: Member?): Member?

    @Throws(SQLException::class)
    fun findById(memberId: String?): Member?

    @Throws(SQLException::class)
    fun update(memberId: String?, money: Int)

    @Throws(SQLException::class)
    fun delete(memberId: String?)
}
