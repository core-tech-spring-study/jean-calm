package com.kakaopaysec.advanced.common

import com.kakaopaysec.filter.LogFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter

@Configuration
class WebConfig {

    @Bean
    fun logFilter(): FilterRegistrationBean<out Filter> {
        val filterRegistry = FilterRegistrationBean<Filter>()
        filterRegistry.filter = LogFilter()
        filterRegistry.order = 1
        filterRegistry.addUrlPatterns("/*")
        return filterRegistry
    }
}
