package com.kakaopaysec.proxy.advisor

import com.kakaopaysec.proxy.pureproxy.proxy.common.advice.TimeAdvice
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ServiceImpl
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ServiceInterface
import mu.KotlinLogging
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.aop.Pointcut
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut

private val logger = KotlinLogging.logger {}

class MultiAdvisorTest1 {

    @Test
    @DisplayName("여러 프록시")
    fun multiAdvisorTest1() {
        // proxy1 생성
        val target = ServiceImpl()
        val proxyFactory1 = ProxyFactory(target)
        val advisor1 = DefaultPointcutAdvisor(Pointcut.TRUE, Advice1())
        proxyFactory1.addAdvisor(advisor1)
        val proxy1 = proxyFactory1.proxy as ServiceInterface

        // proxy2 생성
        val proxyFactory2 = ProxyFactory(proxy1)
        val advisor2 = DefaultPointcutAdvisor(Pointcut.TRUE, Advice2())
        proxyFactory2.addAdvisor(advisor2)
        val proxy2 = proxyFactory2.proxy as ServiceInterface

        // 실행
        proxy2.save()
    }

    @Test
    @DisplayName("하나의 프록시, 여러 어드바이저")
    fun multiAdvisorTest2() {
        // client -> proxy -> advisor2 -> advisor2 -> target
        // proxy1 생성
        val advisor1 = DefaultPointcutAdvisor(Pointcut.TRUE, Advice1())
        val advisor2 = DefaultPointcutAdvisor(Pointcut.TRUE, Advice2())

        val target = ServiceImpl()
        val proxyFactory1 = ProxyFactory(target)

        proxyFactory1.addAdvisor(advisor2)
        proxyFactory1.addAdvisor(advisor1)

        val proxy1 = proxyFactory1.proxy as ServiceInterface

        // 실행
        proxy1.save()
    }

    class Advice1 : MethodInterceptor {
        override fun invoke(invocation: MethodInvocation): Any? {
            logger.info { "advice1 호출" }
            val result = invocation.proceed()
            logger.info { "advice1 종료" }

            return result
        }
    }

    class Advice2 : MethodInterceptor {
        override fun invoke(invocation: MethodInvocation): Any? {
            logger.info { "advice2 호출" }
            val result = invocation.proceed()
            logger.info { "advice2 종료" }

            return result
        }
    }
}
