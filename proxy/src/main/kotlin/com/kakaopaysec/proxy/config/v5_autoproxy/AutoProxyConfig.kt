package com.kakaopaysec.proxy.config.v5_autoproxy

import com.kakaopaysec.proxy.config.AppV1Config
import com.kakaopaysec.proxy.config.AppV2Config
import com.kakaopaysec.proxy.config.v3_proxyfactory.advice.LogTraceAdvice
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.aop.Advisor
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(AppV1Config::class, AppV2Config::class)
@Configuration
class AutoProxyConfig {

    //@Bean
    fun advisor1(trace: LogTrace): Advisor {
        // pointcut
        val pointcut = NameMatchMethodPointcut()
        pointcut.setMappedNames("request*", "order*", "save**")

        // advice
        val advise = LogTraceAdvice(trace)
        return DefaultPointcutAdvisor(pointcut, advise)
    }

    //@Bean
    fun advisor2(trace: LogTrace): Advisor {
        // pointcut
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression = "execution(* com.kakaopaysec.proxy.app..*(..))"

        // advice
        val advise = LogTraceAdvice(trace)
        return DefaultPointcutAdvisor(pointcut, advise)
    }

    @Bean
    fun advisor3(trace: LogTrace): Advisor {
        // pointcut
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression = "execution(* com.kakaopaysec.proxy.app..*(..)) && !execution(* com.kakaopaysec.proxy.app..noLog(..))"

        // advice
        val advise = LogTraceAdvice(trace)
        return DefaultPointcutAdvisor(pointcut, advise)
    }
}
