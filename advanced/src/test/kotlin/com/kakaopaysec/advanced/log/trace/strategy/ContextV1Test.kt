package com.kakaopaysec.advanced.log.trace.strategy

import com.kakaopaysec.advanced.log.trace.strategy.code.strategy.ContextV1
import com.kakaopaysec.advanced.log.trace.strategy.code.strategy.StrategyLogic1
import com.kakaopaysec.advanced.log.trace.strategy.code.strategy.StrategyLogic2
import mu.KotlinLogging
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class ContextV1Test {

    @Test
    fun strategyV0() {
        logic1()
        logic2()
    }

    private fun logic1() {
        val startTime = System.currentTimeMillis()

        // 비즈니스 로직 실행
        logger.info { "비즈니스 로직1 실행" }

        // 비즈니스 로직 종료
        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        logger.info { "resultTime=$resultTime" }
    }

    private fun logic2() {
        val startTime = System.currentTimeMillis()

        // 비즈니스 로직 실행
        logger.info { "비즈니스 로직2 실행" }

        // 비즈니스 로직 종료
        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        logger.info { "resultTime=$resultTime" }
    }

    @Test
    fun strategyV1() {
        val strategyLogic1 = StrategyLogic1()
        val contextV1 = ContextV1(strategyLogic1)
        contextV1.execute()

        val strategyLogic2 = StrategyLogic2()
        val contextV2 = ContextV1(strategyLogic2)
        contextV2.execute()
    }

    @Test
    fun strategyV2() {
        val strategyLogic1 = object : StrategyLogic1() {
            override fun call() {
                logger.info { "비즈니스 로직1 실행" }
            }
        }

        val contextV1 = ContextV1(strategyLogic1)

        logger.info { "strategyLogic1 클래스: ${strategyLogic1.javaClass}" }
        contextV1.execute()

        val strategyLogic2 = object : StrategyLogic1() {
            override fun call() {
                logger.info { "비즈니스 로직2 실행" }
            }
        }

        val contextV2 = ContextV1(strategyLogic2)

        logger.info { "strategyLogic2 클래스: ${strategyLogic2.javaClass}" }
        contextV2.execute()
    }

    /**
     * 전략 패턴 중 필드에 전략을 저장하는 방식은 선 조립 후 실행방법에 적합합니다.
     * context를 실행하는 시점에는 이미 조립이 완료됬기 때문에 전략을 신경쓰지 않고 실행만 하면 됩니다.
     */

    @Test
    fun strategyV3() {

        val contextV1 = ContextV1(object : StrategyLogic1() {
            override fun call() {
                logger.info { "비즈니스 로직1 실행" }
            }
        })

        contextV1.execute()

        val contextV2 = ContextV1(object : StrategyLogic2() {
            override fun call() {
                logger.info { "비즈니스 로직2 실행" }
            }
        })

        contextV2.execute()
    }
}
