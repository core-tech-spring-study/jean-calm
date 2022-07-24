package com.kakaopaysec.advanced.log.trace.callback

interface TraceCallback<T> {
    fun call(): T
}
