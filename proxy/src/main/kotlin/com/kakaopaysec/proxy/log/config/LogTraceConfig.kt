package com.kakaopaysec.proxy.log.config

import com.kakaopaysec.proxy.log.trace.logtrace.FieldLogTrace
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.trace.logtrace.ThreadLocalLogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LogTraceConfig {

    @Bean
    fun trace(): LogTrace {
        return ThreadLocalLogTrace()
    }
}
