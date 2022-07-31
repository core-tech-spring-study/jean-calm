package com.kakaopaysec.proxy.config.v6_aop

import com.kakaopaysec.proxy.config.AppV1Config
import com.kakaopaysec.proxy.config.AppV2Config
import com.kakaopaysec.proxy.config.v6_aop.aspect.LogTraceAspect
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(AppV1Config::class, AppV2Config::class)
@Configuration
class AopConfig {

    @Bean
    fun logTraceAspect(trace: LogTrace): LogTraceAspect {
        return LogTraceAspect(trace)
    }
}

