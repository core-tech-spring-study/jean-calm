package com.kakaopaysec.exception

import com.kakaopaysec.exception.filter.LogFilter
import com.kakaopaysec.exception.interceptor.LogInterceptor
import com.kakaopaysec.exception.resolver.MyHandlerExceptionResolver
import com.kakaopaysec.exception.resolver.UserHandlerExceptionResolver
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.DispatcherType.ERROR
import javax.servlet.DispatcherType.REQUEST
import javax.servlet.Filter

@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LogInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/css/**", "*.ico", "/error", "/error-page/**")
    }

    override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
        resolvers.add(MyHandlerExceptionResolver())
        resolvers.add(UserHandlerExceptionResolver())
    }

    //@Bean
    fun logFilter(): FilterRegistrationBean<out Filter> {
        val filterRegistry = FilterRegistrationBean<Filter>()
        filterRegistry.filter = LogFilter()
        filterRegistry.order = 1
        filterRegistry.addUrlPatterns("/*")
        //filterRegistry.setDispatcherTypes(REQUEST, ERROR)
        return filterRegistry
    }
}
