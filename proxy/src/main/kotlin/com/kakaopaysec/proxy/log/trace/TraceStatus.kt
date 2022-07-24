package com.kakaopaysec.proxy.log.trace

data class TraceStatus(
    val traceId: TraceId,
    val startTimeMs: Long,
    val message: String
)
