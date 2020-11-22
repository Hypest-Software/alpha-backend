package dev.hypestsoftware.hackyeah2020.backend.config

import dev.hypestsoftware.hackyeah2020.backend.model.OAuthRefreshToken
import dev.hypestsoftware.hackyeah2020.backend.repository.OAuthRefreshTokenRepository
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.stereotype.Component
import java.time.Instant
import javax.sql.DataSource

@Component
class CustomJwtTokenStore(
    private val customJdbcTokenStore: CustomJdbcTokenStore,
    jwtAccessTokenConverter: JwtAccessTokenConverter
) : JwtTokenStore(jwtAccessTokenConverter) {

    override fun readRefreshToken(tokenValue: String?): OAuth2RefreshToken? {
        return customJdbcTokenStore.readRefreshToken(tokenValue)
    }

    override fun removeRefreshToken(token: OAuth2RefreshToken?) {
        customJdbcTokenStore.removeRefreshToken(token)
    }

    override fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication) {
        customJdbcTokenStore.storeRefreshToken(refreshToken, authentication)
    }

    fun deleteAllRefreshTokensByUsername(username: String) {
        customJdbcTokenStore.deleteAllRefreshTokensByUsername(username)
    }

    fun deleteExpiredRefreshTokens() {
        customJdbcTokenStore.deleteExpiredRefreshTokens()
    }
}

@Component
class CustomJdbcTokenStore(
    private val oAuthRefreshTokenRepository: OAuthRefreshTokenRepository,
    dataSource: DataSource
) : JdbcTokenStore(dataSource) {

    override fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication) {
        val expiringToken = refreshToken as ExpiringOAuth2RefreshToken

        oAuthRefreshTokenRepository.save(
            OAuthRefreshToken(
                token_id = extractTokenKey(refreshToken.value),
                token = serializeRefreshToken(refreshToken),
                authentication = serializeAuthentication(authentication),
                username = if (authentication.isClientOnly) null else authentication.name,
                expiration = expiringToken.expiration.time
            )
        )
    }

    fun deleteAllRefreshTokensByUsername(username: String): Int {
        return oAuthRefreshTokenRepository.deleteByUsername(username)
    }

    fun deleteExpiredRefreshTokens(): Int {
        val nowInMillis = Instant.now().toEpochMilli()
        return oAuthRefreshTokenRepository.deleteByExpirationLessThan(nowInMillis)
    }
}
