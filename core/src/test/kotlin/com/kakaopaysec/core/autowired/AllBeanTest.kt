package com.kakaopaysec.core.autowired

import com.kakaopaysec.core.AutoAppConfig
import com.kakaopaysec.core.discount.DiscountPolicy
import com.kakaopaysec.core.member.domain.Grade
import com.kakaopaysec.core.member.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class AllBeanTest {

    @Test
    fun findAllBean() {
        val ac = AnnotationConfigApplicationContext(AutoAppConfig::class.java, DiscountService::class.java)

        val discountService = ac.getBean(DiscountService::class.java)
        val member = Member(1L, "jean", Grade.VIP)
        val discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy")

        assertThat(discountService).isInstanceOf(DiscountService::class.java)
        assertThat(discountPrice).isEqualTo(1000)

        val rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy")
        assertThat(rateDiscountPrice).isEqualTo(2000)
    }

    class DiscountService(
        private val policyMap: MutableMap<String, DiscountPolicy>,
        private val policies: MutableList<DiscountPolicy>,
    ) {

        fun discount(member: Member, price: Int, discountCode : String): Int {
            val discountPolicy = policyMap[discountCode]
            return discountPolicy?.discount(member, price) ?: 0
        }

        init {
            println("policyMap = $policyMap")
            println("policies = $policies")
        }
    }
}
