package com.kakaopaysec.core.lifecycle

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

class NetworkClient {

    var url: String? = null

    init {
        println("생성자 호출, url: $url")
    }

    // 서비스 시작시 호출
    private fun connect() {
        println("connect: $url")
    }

    private fun call(message: String) {
        println("call: $url, message = $message")
    }

    // 서비스 종료 시 호출
    fun disconnect() {
        println("close: $url")
    }

     @PostConstruct
     fun init() {
        connect()
        call("초기화 연결 메시지")
    }

     @PreDestroy
     fun close() {
        disconnect()
    }
}
