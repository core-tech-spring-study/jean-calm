package com.kakaopaysec.proxy.log.trace.callback

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.beans.factory.annotation.Qualifier

class TraceTemplate(
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    /**
     * 템플릿 콜백 패턴 구현
     * kotlin - generic 함수 정의 시 타입이 정해지지 않는 변수(ex TraceCallback)인 경우
     * 함수 이름 앞에 <T>처럼 정의되어야 합니다.
     */
    fun <T> execute(message: String, callback: TraceCallback<T>): T {

        var status: TraceStatus? = null

        try {
            status = trace.begin(message)

            // 로직 소출
            val result  = callback.call()

            trace.end(status)

            return result
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
