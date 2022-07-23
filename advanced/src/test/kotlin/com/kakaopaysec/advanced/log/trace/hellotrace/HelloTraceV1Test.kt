package com.kakaopaysec.advanced.log.trace.hellotrace

import com.kakaopaysec.advanced.log.trace.hellotrace.HelloTraceV1
import org.junit.jupiter.api.Test


internal class HelloTraceV1Test {

    @Test
    fun begin_end() {
        // given
        val trace = HelloTraceV1()
        //when
        val status = trace.begin("hello" )
        trace.end(status)
    }

    @Test
    fun test() {
        // given
        val trace = HelloTraceV1()
        //when
        val status = trace.begin("hello")
        trace.exception(status, IllegalStateException())
    }
}
