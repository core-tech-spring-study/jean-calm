package com.kakaopaysec.proxy.config.v4_postprocessor.postprocessor

import com.kakaopaysec.proxy.config.AppV1Config
import com.kakaopaysec.proxy.config.AppV2Config
import com.kakaopaysec.proxy.config.v3_proxyfactory.advice.LogTraceAdvice
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.aop.Advisor
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(AppV1Config::class, AppV2Config::class)
@Configuration
class BeanPostProcessorConfig {

    @Bean
    fun packageLogTracePostProcessor(trace: LogTrace): PackageLogTracePostProcessor {
        return PackageLogTracePostProcessor("com.kakaopaysec.proxy.app", getAdvisor(trace))
    }

    private fun getAdvisor(trace: LogTrace): Advisor {
        // pointcut
        val pointcut = NameMatchMethodPointcut()
        pointcut.setMappedNames("request*", "order*", "save**")

        // advice
        val advise = LogTraceAdvice(trace)
        return DefaultPointcutAdvisor(pointcut, advise)
    }
}
