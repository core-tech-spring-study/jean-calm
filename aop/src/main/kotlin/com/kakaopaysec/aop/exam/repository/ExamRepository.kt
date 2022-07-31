package com.kakaopaysec.aop.exam.repository

import com.kakaopaysec.aop.exam.annotation.Retry
import com.kakaopaysec.aop.exam.annotation.Trace
import mu.KotlinLogging
import org.springframework.stereotype.Repository

private val logger = KotlinLogging.logger {}

@Repository
class ExamRepository {

    @Trace
    @Retry(value = 4)
    fun save(itemId: String): String {
        seq = seq.plus(1)

        logger.info { "seq: $seq" }

        if (seq % 5 == 0) {
            throw IllegalStateException("예외 발생")
        }

        return "OK"
    }

    companion object {
        private var seq: Int = 0
    }
}
