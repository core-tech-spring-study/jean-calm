package com.kakaopaysec.springtx.order

import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

private val logger = KotlinLogging.logger {}

@SpringBootTest
internal class OrderServiceTest {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    fun complete() {
        // given
        val order = Order()
        order.username = "정상"

        // when
        orderService.order(order)

        // then
        val findOrder = orderRepository.findByIdOrNull(order.id)

        assertThat(findOrder?.payStatus).isEqualTo("완료")
    }

    @Test
    fun runtimeException() {
        // given
        val order = Order()
        order.username = "예외"

        // when
        assertThatThrownBy {
            orderService.order(order)
        }.isInstanceOf(RuntimeException::class.java)

        // then
        val findOrder = orderRepository.findByIdOrNull(order.id)

        assertThat(findOrder).isNull()
    }

    @Test
    fun bizException() {
        // given
        val order = Order()
        order.username = "잔고부족"

        // when
        try {
            orderService.order(order)
        } catch (e: NotEnoughMoneyException) {
            logger.info { "고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내" }
        }

        // then
        val findOrder = orderRepository.findByIdOrNull(order.id)

        assertThat(findOrder?.payStatus).isEqualTo("대기")
    }
}

