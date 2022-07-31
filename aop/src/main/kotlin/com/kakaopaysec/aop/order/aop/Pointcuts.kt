package com.kakaopaysec.aop.order.aop

import org.aspectj.lang.annotation.Pointcut

class Pointcuts {

    @Pointcut("execution(* com.kakaopaysec.aop.order..*(..))")
    fun allOrder() {} // pointcut signature

    @Pointcut("execution(* *..*Service.*(..))")
    fun allService() {} // pointcut signature

    @Pointcut("allOrder() && allService()")
    fun orderAndService() {}
}
