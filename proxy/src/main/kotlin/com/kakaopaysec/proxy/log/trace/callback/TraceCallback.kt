package com.kakaopaysec.proxy.log.trace.callback

interface TraceCallback<T> {
    fun call(): T
}
