package com.kakaopaysec.springtx

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.StopWatch
import java.util.concurrent.TimeUnit

@SpringBootTest
class SpringtxApplicationTests {

    @Test
    fun contextLoads() {
        val stopWatch = StopWatch()
        stopWatch.start()

        println("SpringTx Start!")
        println(Thread.currentThread().name)

        //doSomething()
        doSomethingByAsync()

        stopWatch.stop()
        println("소요시간 = ${stopWatch.totalTimeMillis}ms")
    }

    private fun doSomething() {
        println(Thread.currentThread().name)
        TimeUnit.SECONDS.sleep(5)
    }
    private fun doSomethingByAsync() {
        Thread {
            try {
                println(Thread.currentThread().name)
                TimeUnit.SECONDS.sleep(5)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }
}
