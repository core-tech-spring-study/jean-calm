package com.kakaopaysec.proxy.log.trace.hellotrace

import com.kakaopaysec.proxy.log.trace.TraceId
import com.kakaopaysec.proxy.log.trace.TraceStatus
import mu.KotlinLogging
import org.springframework.stereotype.Component


private val logger = KotlinLogging.logger {}

@Component
class HelloTraceV2 {

    fun begin(message: String): TraceStatus {
        val traceId = TraceId()
        val startTimeMs = System.currentTimeMillis()
        logger.info { "[${traceId.id}] ${addSpace(START_PREFIX, traceId.level)} $message"}
        return TraceStatus(traceId, startTimeMs, message)
    }

    fun beginSync(beforeTraceId: TraceId, message: String): TraceStatus {
        val nextId = beforeTraceId.createNextId()
        val startTimeMs = System.currentTimeMillis()
        logger.info { "[${nextId.id}] ${addSpace(START_PREFIX, nextId.level)} $message"}
        return TraceStatus(nextId, startTimeMs, message)
    }

    fun end(status: TraceStatus) {
        complete(status, null)
    }

    fun exception(status: TraceStatus, e: Exception) {
        complete(status, e)
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTimeMs = System.currentTimeMillis()
        val resultTimeMs = stopTimeMs - status.startTimeMs
        val traceId = status.traceId

        when (e) {
            null -> logger.info { "[${traceId.id}] ${addSpace(COMPLETE_PREFIX, traceId.level)} ${status.message} time=${resultTimeMs}ms" }
            else -> logger.info { "[${traceId.id}] ${addSpace(EX_PREFIX, traceId.level)} ${status.message} time=${resultTimeMs}ms ex=$e" }
        }
    }

    // level = 0, level = 1 | -->, level = 2 |  | -->
    private fun addSpace(prefix: String, level: Int): String {

        val sb = StringBuilder()

        for (i in 0 until level) {
            sb.append( if (i == level - 1) "|$prefix" else "|  ")
        }

        return sb.toString()
    }
}
