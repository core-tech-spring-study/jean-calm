package com.kakaopaysec.core.member.domain

class Member(
    var id: Long,
    var name: String,
    var grade: Grade
) {
    override fun toString(): String {
        return "Member(id=$id, name='$name', grade=$grade)"
    }
}
