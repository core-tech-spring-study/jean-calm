package com.kakaopaysec.aop.internal

import com.kakaopaysec.aop.internal.aop.CallLogAspect
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(CallLogAspect::class)
@SpringBootTest
class CallServiceV2Test {

    @Autowired lateinit var callServiceV2: CallServiceV2

    @Test
    fun test() {
        callServiceV2.external()
    }
}
