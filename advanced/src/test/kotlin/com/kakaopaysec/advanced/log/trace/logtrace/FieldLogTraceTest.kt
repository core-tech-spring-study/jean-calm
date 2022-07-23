package com.kakaopaysec.advanced.log.trace.logtrace

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired

internal class FieldLogTraceTest {

    private val trace = FieldLogTrace()

    @Test
    fun begin_end_level2() {
        val status1 = trace.begin("hello1")
        val status2 = trace.begin("hello2")
        trace.end(status2)
        trace.end(status1)
    }

    @Test
    fun begin_exception_level2() {
        val status1 = trace.begin("hello1")
        val status2 = trace.begin("hello2")
        trace.exception(status2, IllegalStateException())
        trace.exception(status1, IllegalStateException())
    }
}
