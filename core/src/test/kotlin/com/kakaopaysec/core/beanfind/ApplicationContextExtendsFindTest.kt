package com.kakaopaysec.core.beanfind

import com.kakaopaysec.core.discount.DiscountPolicy
import com.kakaopaysec.core.discount.FixDiscountPolicy
import com.kakaopaysec.core.discount.RateDiscountPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

internal class ApplicationContextExtendsFindTest  {

    lateinit var ac: AnnotationConfigApplicationContext

    @BeforeEach
    fun setUp() {
        ac = AnnotationConfigApplicationContext(TestConfig::class.java)
    }

    /**
     * 기본적으로 스프링 컨테이너에서 부모 타입의 빈을 조회하면 상속받은 모든 자식 빈들도 조회가 되기 때문에
     * 이 경우에는 빈 이름이 없을 경우 NoSuchBeanDefinitionException이 발생할 수 있기 때문에
     * 조회 시 명시적으로 가져올 자식 빈 이름을 기입해야 합니다.
     */

    @Test
    @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 중복 오류가 발생한다.")
    fun findBeanByParentTypeDuplicate() {
        assertThrows(NoSuchBeanDefinitionException::class.java) {
            ac.getBean(DiscountPolicy::class.java)
        }
    }

    @Test
    @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
    fun findBeanByParentTypeBeanName() {
        ac.getBean("rateDiscountPolicy", DiscountPolicy::class.java)
    }

    @Test
    @DisplayName("특정 하위타입으로 조회")
    fun findBeanBySubType() {
        val bean = ac.getBean(RateDiscountPolicy::class.java)
        assertThat(bean).isInstanceOf(DiscountPolicy::class.java)
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    fun findAllBeanByParentType() {
        val beansOfType = ac.getBeansOfType(DiscountPolicy::class.java)
        assertThat(beansOfType.size).isEqualTo(2)

        beansOfType.forEach {
            println("key = ${it.key}, value = ${it.value}")
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Any")
    fun findAllBeanByAnyType() {
        val beansOfType = ac.getBeansOfType(Any::class.java)

        beansOfType.forEach {
            println("key = ${it.key}, value = ${it.value}")
        }
    }

    @Configuration
    class TestConfig {

        @Bean
        fun rateDiscountPolicy(): DiscountPolicy {
            return RateDiscountPolicy()
        }

        @Bean
        fun fixDiscountPolicy(): DiscountPolicy {
            return FixDiscountPolicy()
        }
    }
}
