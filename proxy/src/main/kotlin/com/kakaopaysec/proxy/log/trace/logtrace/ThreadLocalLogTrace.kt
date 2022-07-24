package com.kakaopaysec.proxy.log.trace.logtrace

import com.kakaopaysec.proxy.log.trace.TraceId
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.hellotrace.COMPLETE_PREFIX
import com.kakaopaysec.proxy.log.trace.hellotrace.EX_PREFIX
import com.kakaopaysec.proxy.log.trace.hellotrace.START_PREFIX
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class ThreadLocalLogTrace: LogTrace {

    private var traceIdHolder: ThreadLocal<TraceId> = ThreadLocal()

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId = traceIdHolder.get()
        val startTimeMs = System.currentTimeMillis()
        logger.info { "[${traceId.id}] ${addSpace(START_PREFIX, traceId.level)} $message"}
        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        val traceId = traceIdHolder.get()

        if (traceId == null) {
            traceIdHolder.set(TraceId())
        } else {
            traceIdHolder.set(traceId.createNextId())
        }
    }

    override fun end(status: TraceStatus) {
        complete(status, null)
    }

    override fun exception(status: TraceStatus, e: Exception) {
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

        releaseTraceId()
    }

    private fun releaseTraceId() {
        val traceId = traceIdHolder.get()

        if (traceId.isFirstLevel()) {
            traceIdHolder.remove() // destroy
        } else {
            traceIdHolder.set(traceId.createPreviousId())
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
