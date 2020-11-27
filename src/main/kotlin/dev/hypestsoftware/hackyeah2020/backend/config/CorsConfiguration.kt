package dev.hypestsoftware.hackyeah2020.backend.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.servlet.Filter

@Configuration
class CorsConfiguration {
    // IMPORTANT: it has to be a normal configuration class,
    // not extending WebMvcConfigurerAdapter or other Spring Security class
    @Bean
    fun customCorsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("http://localhost:4200")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        val bean: FilterRegistrationBean<*> = FilterRegistrationBean<Filter>(CorsFilter(source))
        // IMPORTANT #2: I didn't stress enough the importance of this line in my original answer,
        // but it's here where we tell Spring to load this filter at the right point in the chain
        // (with an order of precedence higher than oauth2's filters)
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }
}