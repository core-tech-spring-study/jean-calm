package com.kakaopaysec.proxy.log.trace.logtrace

import com.kakaopaysec.proxy.log.trace.TraceStatus

interface LogTrace {
    fun begin(message: String): TraceStatus
    fun end(status: TraceStatus)
    fun exception(status: TraceStatus, e: Exception)
}
