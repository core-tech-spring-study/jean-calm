package com.kakaopaysec.proxy

import com.kakaopaysec.proxy.config.AppV1Config
import com.kakaopaysec.proxy.config.AppV2Config
import com.kakaopaysec.proxy.config.v1_proxy.ConcreteProxyConfig
import com.kakaopaysec.proxy.config.v1_proxy.InterfaceProxyConfig
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.trace.logtrace.ThreadLocalLogTrace
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

//@Import(AppV1Config::class, AppV2Config::class)
@Import(InterfaceProxyConfig::class, ConcreteProxyConfig::class)
@SpringBootApplication(scanBasePackages = ["com.kakaopaysec.proxy.app"])
class ProxyApplication {
    @Bean
    fun trace(): LogTrace {
        return ThreadLocalLogTrace()
    }
}

fun main(args: Array<String>) {
    runApplication<ProxyApplication>(*args)
}


