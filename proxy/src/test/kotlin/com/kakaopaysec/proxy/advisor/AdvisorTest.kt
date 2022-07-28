package com.kakaopaysec.proxy.advisor

import com.kakaopaysec.proxy.pureproxy.proxy.common.advice.TimeAdvice
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ServiceImpl
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ServiceInterface
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.aop.ClassFilter
import org.springframework.aop.MethodMatcher
import org.springframework.aop.Pointcut
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import java.lang.reflect.Method

private val logger = KotlinLogging.logger {}

class AdvisorTest {

    @Test
    @DisplayName("Pointcut과 advise를 참조하고 있는 advisor를 생성하여 proxy에 추가하여 target을 호출한다.")
    fun advisorTest1() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)
        val advisor = DefaultPointcutAdvisor(Pointcut.TRUE, TimeAdvice())
        proxyFactory.addAdvisor(advisor)

        val proxy = proxyFactory.proxy as ServiceInterface

        proxy.save()
        proxy.find()
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    fun advisorTest2() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)
        val advisor = DefaultPointcutAdvisor(MyPointCut(), TimeAdvice())
        proxyFactory.addAdvisor(advisor)

        val proxy = proxyFactory.proxy as ServiceInterface

        proxy.save()
        proxy.find()
    }

    class MyPointCut: Pointcut {

        override fun getClassFilter(): ClassFilter {
            return ClassFilter.TRUE
        }

        override fun getMethodMatcher(): MethodMatcher {
            return MyMethodMatcher()
        }
    }

    class MyMethodMatcher: MethodMatcher {

        private val matchName = "save"

        override fun matches(method: Method, targetClass: Class<*>): Boolean {
            val result = method.name.equals(matchName)
            logger.info { "포인트컷 호출 method = ${method.name}, targetClass = $targetClass" }
            logger.info { "포인트컷 결과 result = $result" }
            return result
        }

        override fun matches(method: Method, targetClass: Class<*>, vararg args: Any?): Boolean {
            return false
        }

        override fun isRuntime(): Boolean {
            return false
        }
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    fun advisorTest3() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)
        val nameMethodPointcut = NameMatchMethodPointcut()
        nameMethodPointcut.setMappedName("save")

        val advisor = DefaultPointcutAdvisor(nameMethodPointcut, TimeAdvice())
        proxyFactory.addAdvisor(advisor)

        val proxy = proxyFactory.proxy as ServiceInterface

        proxy.save()
        proxy.find()
    }
}
