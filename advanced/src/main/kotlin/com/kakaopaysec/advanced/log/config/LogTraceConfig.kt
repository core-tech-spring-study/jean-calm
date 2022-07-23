package com.kakaopaysec.advanced.log.config

import com.kakaopaysec.advanced.log.trace.logtrace.FieldLogTrace
import com.kakaopaysec.advanced.log.trace.logtrace.LogTrace
import com.kakaopaysec.advanced.log.trace.logtrace.ThreadLocalLogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LogTraceConfig {
    
    @Bean
    fun fieldLogTrace(): LogTrace {
        return FieldLogTrace()
    }

    @Bean
    fun threadLocalLogTrace(): LogTrace {
        return ThreadLocalLogTrace()
    }
}
