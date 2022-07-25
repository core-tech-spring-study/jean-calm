package com.kakaopaysec.proxy

import com.kakaopaysec.proxy.config.AppV1Config
import com.kakaopaysec.proxy.config.AppV2Config
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(AppV1Config::class, AppV2Config::class)
@SpringBootApplication(scanBasePackages = ["com.kakaopaysec.proxy.app"])
class ProxyApplication

fun main(args: Array<String>) {
    runApplication<ProxyApplication>(*args)
}
