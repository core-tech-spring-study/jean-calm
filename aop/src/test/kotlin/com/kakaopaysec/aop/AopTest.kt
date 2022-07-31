package com.kakaopaysec.aop

import com.kakaopaysec.aop.order.aop.*
import com.kakaopaysec.aop.order.repository.OrderRepository
import com.kakaopaysec.aop.order.service.OrderService
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

private val logger = KotlinLogging.logger {}

//@Import(AspectV1::class)
//@Import(AspectV2::class)
//@Import(AspectV3::class)
//@Import(AspectV4Pointcut::class)
//@Import(AspectV5Order.LogAspect::class, AspectV5Order.TransactionAspect::class)
@Import(AspectV6Advice::class)
@SpringBootTest
class AopTest {

    @Autowired lateinit var orderService: OrderService
    @Autowired lateinit var orderRepository: OrderRepository


    @Test
    fun hasBean() {
        // @Import 어노테이션은 외부 모듈의 빈 설정 파일을 configuration 파일에 GroupBy를 하기 위해 가져올때 사용하기도 하지만,
        // 별도로 스프링 빈으로 등록해주는 기능도 존재함.
        //logger.info { "orderRepository2: ${orderRepository2.javaClass}" }
    }

    @Test
    fun aopInfo() {
        logger.info { "isAopProxy, orderService = ${AopUtils.isAopProxy(orderService)}" }
        logger.info { "isAopProxy, orderRepository = ${AopUtils.isAopProxy(orderRepository)}" }
    }

    @Test
    fun success() {
        orderService.orderItem("itemA")
    }

    @Test
    fun exception() {
        assertThatThrownBy { orderService.orderItem("ex") }
            .isInstanceOf(IllegalStateException::class.java)
    }
}
