package com.kakaopaysec.aop.internal

import com.kakaopaysec.aop.internal.aop.CallLogAspect
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

private val logger = KotlinLogging.logger {}

@Import(CallLogAspect::class)
@SpringBootTest
internal class CallServiceV0Test {

    @Autowired lateinit var callServiceV0: CallServiceV0

    /**
     * 중요사항: callServiceV0의 external(), internal() 모두 포인트컷 대상자지만, 실제로 advice가 적용된건 external() 뿐입니다.
     * 원인은 클라이언트가  Proxy의 external()을 호출하면 advice 적용 후 실제 target(CallServiceV0)의 external() 메서드를 호출하게 되는데
     * 그리고 나서 자기 자신의 내부 메서드를 호출하기 때문에 Proxy에서는 알수가 없어서 어드바이스도 적용이 불가합니다.
     * 결론은 이러한 내부호출은 프록시를 거치지 않는다는 것을 명심하자...
     */
    @Test
    fun external() {
        logger.info { "target = {${callServiceV0.javaClass}}" }
        callServiceV0.external()
    }

    @Test
    fun internal() {
        callServiceV0.internal()
    }
}
