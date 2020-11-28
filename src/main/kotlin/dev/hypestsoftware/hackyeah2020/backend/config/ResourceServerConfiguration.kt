package dev.hypestsoftware.hackyeah2020.backend.config

import dev.hypestsoftware.hackyeah2020.backend.exception.base.RestExceptionHandler
import dev.hypestsoftware.hackyeah2020.backend.utils.PUBLIC_API_ENDPOINT_V1
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.stereotype.Component

@Component
class AuthResponseExceptionTranslator(private val exceptionHandler: RestExceptionHandler) :
    WebResponseExceptionTranslator<Any> {
    override fun translate(e: java.lang.Exception): ResponseEntity<Any> {
        return exceptionHandler.handleApiException(e)
    }
}

@Configuration
@EnableResourceServer
class ResourceServerConfiguration(
    private val tokenStore: TokenStore,
    private val authResponseExceptionTranslator: AuthResponseExceptionTranslator
) : ResourceServerConfigurerAdapter() {
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        val entryPoint =
            OAuth2AuthenticationEntryPoint().apply { setExceptionTranslator(authResponseExceptionTranslator) }
        val deniedHandler =
            OAuth2AccessDeniedHandler().apply { setExceptionTranslator(authResponseExceptionTranslator) }
        resources.tokenStore(tokenStore).accessDeniedHandler(deniedHandler).authenticationEntryPoint(entryPoint)
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers().frameOptions().disable().and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/status").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("$PUBLIC_API_ENDPOINT_V1/reports/**").permitAll()
            .antMatchers("$PUBLIC_API_ENDPOINT_V1/users/register").permitAll()
            .anyRequest().authenticated()
    }
}
