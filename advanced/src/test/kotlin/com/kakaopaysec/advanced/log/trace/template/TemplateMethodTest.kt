package com.kakaopaysec.advanced.log.trace.template

import com.kakaopaysec.advanced.log.trace.template.code.AbstractTemplate
import com.kakaopaysec.advanced.log.trace.template.code.SubClassLogic1
import com.kakaopaysec.advanced.log.trace.template.code.SubClassLogic2
import mu.KotlinLogging
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class TemplateMethodTest {

    @Test
    fun templateMethodV0() {
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
    fun templateMethodV1() {
        val abstractTemplate1 = SubClassLogic1()
        abstractTemplate1.execute()

        val abstractTemplate2 = SubClassLogic2()
        abstractTemplate2.execute()
    }

    @Test
    fun templateMethodV2() {

        // 익명내부 클래스 구현
        val abstractTemplate1 = object : AbstractTemplate() {
            override fun call() {
                logger.info { "비즈니스 로직1 실행" }
            }
        }

        logger.info { "클래스 이름1: ${abstractTemplate1.javaClass}" }
        abstractTemplate1.execute()

        val abstractTemplate2 = object : AbstractTemplate() {
            override fun call() {
                logger.info { "비즈니스 로직2 실행" }
            }
        }

        logger.info { "클래스 이름2: ${abstractTemplate2.javaClass}" }
        abstractTemplate2.execute()
    }
}

