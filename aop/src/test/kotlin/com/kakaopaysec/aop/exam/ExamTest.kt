package com.kakaopaysec.aop.exam

import com.kakaopaysec.aop.exam.aop.RetryAspect
import com.kakaopaysec.aop.exam.aop.TraceAspect
import com.kakaopaysec.aop.exam.service.ExamService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TraceAspect::class, RetryAspect::class)
@SpringBootTest
class ExamTest {

    @Autowired lateinit var examService: ExamService

    @Test
    fun test() {
        for (i in 0 until 5) {
            examService.request("data$i")
        }
    }
}
