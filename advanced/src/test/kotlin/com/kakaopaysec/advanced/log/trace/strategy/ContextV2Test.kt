package com.kakaopaysec.advanced.log.trace.strategy

import com.kakaopaysec.advanced.log.trace.strategy.code.strategy.ContextV2
import com.kakaopaysec.advanced.log.trace.strategy.code.strategy.StrategyLogic1
import com.kakaopaysec.advanced.log.trace.strategy.code.strategy.StrategyLogic2
import org.junit.jupiter.api.Test

class ContextV2Test {

    /**
     * 전략 패턴 적용
     * 필드 주입이 아니라 파라미터에 전략을 넘기는 방식의 장점은 context를 실행할 때마다 유연하게 전략 변경이 가능함.
     * 단점은 역시 실행할 때마다 전략을 계속 지정해줘야 합니다.
     */
    @Test
    fun strategyV1() {
        val context = ContextV2()
        context.execute(StrategyLogic1())
        context.execute(StrategyLogic2())
    }
}
