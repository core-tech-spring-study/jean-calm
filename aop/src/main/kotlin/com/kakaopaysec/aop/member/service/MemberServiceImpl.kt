package com.kakaopaysec.aop.member.service

import com.kakaopaysec.aop.member.annotation.ClassAop
import com.kakaopaysec.aop.member.annotation.MethodAop
import org.springframework.stereotype.Component

@ClassAop
@Component
class MemberServiceImpl: MemberService {

    @MethodAop(value = "test value")
    override fun hello(param: String): String {
        return "OK"
    }

    fun internal(param: String): String {
        return "OK"
    }
}
