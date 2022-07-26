package com.kakaopaysec.proxy.pureproxy.decorator

import com.kakaopaysec.proxy.pureproxy.decorator.code.*
import org.junit.jupiter.api.Test

class DecoratorPatternTest {

    @Test
    fun noDecorator() {
        val realComponent = RealComponent()
        val decoratorPatternClient = DecoratorPatternClient(realComponent)
        decoratorPatternClient.execute()
    }

    @Test
    fun decorator1() {
        val component = RealComponent()
        val messageDecorator = MessageDecorator(component)
        val decoratorPatternClient = DecoratorPatternClient(messageDecorator)
        decoratorPatternClient.execute()
    }

    @Test
    fun decorator2() {
        val component = RealComponent()
        val messageDecorator = MessageDecorator(component)
        val timeDecorator = TimeDecorator(messageDecorator)
        val decoratorPatternClient = DecoratorPatternClient(timeDecorator)
        decoratorPatternClient.execute()
    }

    @Test
    fun decorator3() {
        val component = RealComponent()
        val abstractDecorator = ConcreteDecorator(component)
        val decoratorPatternClient = DecoratorPatternClient(abstractDecorator)
        decoratorPatternClient.execute()
    }
}
