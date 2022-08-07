package com.kakaopaysec.core.scope

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

internal class PrototypeTest {

    @Test
    fun prototypeBeanFind() {
        val ac = AnnotationConfigApplicationContext(PrototypeBean::class.java)
        println("find prototypeBean1")
        val prototypeBean1 = ac.getBean(PrototypeBean::class.java)
        println("find prototypeBean2")
        val prototypeBean2 = ac.getBean(PrototypeBean::class.java)

        println("prototypeBean1 = $prototypeBean1")
        println("prototypeBean2 = $prototypeBean2")

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2)

        prototypeBean1.destroy()
        prototypeBean2.destroy()

        ac.close()
    }

    @Scope("prototype")
    class PrototypeBean {

        @PostConstruct
        fun init() {
            println("prototype init")
        }

        @PreDestroy
        fun destroy() {
            println("prototype destroy ")
        }
    }
}
