package com.kakaopaysec.advanced.log.trace.logtrace

import com.kakaopaysec.advanced.log.trace.TraceStatus

interface LogTrace {
    fun begin(message: String): TraceStatus
    fun end(status: TraceStatus)
    fun exception(status: TraceStatus, e: Exception)
}
