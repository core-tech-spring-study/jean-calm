package com.kakaopaysec.core.annotation

import org.springframework.beans.factory.annotation.Qualifier
import java.lang.annotation.Inherited
import kotlin.annotation.AnnotationTarget.*

@Target(CLASS, FIELD, FUNCTION, VALUE_PARAMETER, TYPE, ANNOTATION_CLASS, CONSTRUCTOR, LOCAL_VARIABLE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Qualifier("mainDiscountPolicy")
annotation class MainDiscountPolicy {
}
