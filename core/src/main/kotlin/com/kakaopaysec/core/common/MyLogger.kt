package com.kakaopaysec.core.common

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class MyLogger {

    var uuid: String? = null
    var requestUrl: String? = null

    fun log(message: String) {
        println("[ $uuid ]  [ $requestUrl ] $message")
    }

    @PostConstruct
    fun init() {
        uuid = UUID.randomUUID().toString()
        println("[ $uuid ] request scope bean create: $this")
    }

    @PreDestroy
    fun close() {
        println("[ $uuid ] request scope bean close: $this")
    }
}
