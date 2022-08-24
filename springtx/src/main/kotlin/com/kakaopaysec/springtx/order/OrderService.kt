package com.kakaopaysec.springtx.order

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    // JPA는 트랜잭션 커밋 시점에 Order 데이터를 DB에 반영한다.
    @Transactional
    fun order(order: Order) {
        logger.info { "order 호출" }
        orderRepository.save(order)

        logger.info { "결제 프로세스 진입" }
        if (order.username.equals("예외")) {
            logger.info { "시스템 예외 발생" }
            throw RuntimeException("시스템 예외")
        } else if (order.username.equals("잔고부족")) {
            logger.info { "잔고부족 비즈니스 예외" }
            order.payStatus = "대기"
            throw NotEnoughMoneyException("잔고가 부족합니다. ")
        } else {
            logger.info { "정상 승인" }
            order.payStatus = "완료"
        }
        logger.info { "결제 프로세스 완료" }
    }
}
