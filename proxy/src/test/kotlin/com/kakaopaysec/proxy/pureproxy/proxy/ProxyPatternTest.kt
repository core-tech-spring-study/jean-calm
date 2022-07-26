package com.kakaopaysec.proxy.pureproxy.proxy

import com.kakaopaysec.proxy.pureproxy.proxy.code.CacheProxy
import com.kakaopaysec.proxy.pureproxy.proxy.code.ProxyPatternClient
import com.kakaopaysec.proxy.pureproxy.proxy.code.RealSubject
import org.junit.jupiter.api.Test

class ProxyPatternTest {

    @Test
    fun noProxyTest() {
        val realSubject = RealSubject()
        val proxyPatternClient = ProxyPatternClient(realSubject)

        proxyPatternClient.execute()
        proxyPatternClient.execute()
        proxyPatternClient.execute()
    }

    @Test
    fun cacheProxyTest() {
        // given
        val target = RealSubject()
        val cacheProxy = CacheProxy(target)
        val proxyPatternClient = ProxyPatternClient(cacheProxy)

        // when
        proxyPatternClient.execute()
        proxyPatternClient.execute()
        proxyPatternClient.execute()
    }
}
