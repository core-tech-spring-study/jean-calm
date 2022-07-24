package com.kakaopaysec.advanced.log.trace.template

import com.kakaopaysec.advanced.log.trace.TraceStatus
import com.kakaopaysec.advanced.log.trace.logtrace.LogTrace
import org.springframework.beans.factory.annotation.Qualifier

abstract class AbstractTemplate<T>(
    @Qualifier("threadLocalLogTrace")  private val trace: LogTrace
) {

    fun execute(message: String): T {

        var status: TraceStatus? = null

        try {
            status = trace.begin(message)

            // 로직 소출
            val result  = call()

            trace.end(status)

            return result
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }

    protected abstract fun call(): T
}
