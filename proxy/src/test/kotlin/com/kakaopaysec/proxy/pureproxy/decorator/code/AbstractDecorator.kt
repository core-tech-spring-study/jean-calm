package com.kakaopaysec.proxy.pureproxy.decorator.code

abstract class AbstractDecorator(
    private val component: Component
): Component {
    override fun operation(): String {
        return component.operation()
    }
}
