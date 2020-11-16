package dev.hypestsoftware.hackyeah2020.backend.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.web.context.annotation.RequestScope
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class JwtField(val fieldKey: String)

open class TokenInfo(
    @property:JwtField("user_uuid")
    open var userUUID: String? = null,
    @property:JwtField("user_name")
    open var userName: String? = null,
) {
    override fun toString(): String {
        return "TokenInfo(userUUID=$userUUID, userName=$userName)"
    }
}

abstract class TokenInfoConfiguration {
    private val tokenInfoFields = TokenInfo::class.memberProperties
        .filter { it.findAnnotation<JwtField>() != null }
        .mapNotNull { it as? KMutableProperty<*> }

    @Bean
    @RequestScope
    abstract fun createTokenInfo(): TokenInfo

    protected fun fillTokenInfoFromAuthenticationDetails(
        tokenInfo: TokenInfo,
        authenticationDetails: OAuth2AuthenticationDetails,
        tokenStore: TokenStore
    ) {
        val oAuth2AccessToken = tokenStore.readAccessToken(authenticationDetails.tokenValue)
        tokenInfoFields.forEach {
            // it can not be null because it was checked before.(see field declaration)
            val key = it.findAnnotation<JwtField>()!!.fieldKey
            val value = oAuth2AccessToken.additionalInformation[key]
            it.setter.call(tokenInfo, value)
        }
    }
}

@Configuration
class TokenInfoConfigurationImpl : TokenInfoConfiguration() {
    @Autowired
    private lateinit var tokenStore: TokenStore

    override fun createTokenInfo(): TokenInfo {
        val authentication = SecurityContextHolder.getContext().authentication
        val authenticationDetails = authentication.details
        val tokenInfo = TokenInfo()
        if (authenticationDetails is OAuth2AuthenticationDetails) {
            fillTokenInfoFromAuthenticationDetails(tokenInfo, authenticationDetails, tokenStore)
        }
        return tokenInfo
    }
}
