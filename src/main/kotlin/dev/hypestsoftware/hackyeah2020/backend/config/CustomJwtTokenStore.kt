package dev.hypestsoftware.hackyeah2020.backend.config

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.support.SqlLobValue
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.stereotype.Component
import java.sql.Types
import java.time.Instant
import javax.sql.DataSource

@Component
class CustomJwtTokenStore(
    private val customJdbcTokenStore: CustomJdbcTokenStore,
    jwtAccessTokenConverter: JwtAccessTokenConverter) : JwtTokenStore(jwtAccessTokenConverter) {

    override fun readRefreshToken(tokenValue: String?): OAuth2RefreshToken? {
        return customJdbcTokenStore.readRefreshToken(tokenValue)
    }

    override fun removeRefreshToken(token: OAuth2RefreshToken?) {
        customJdbcTokenStore.removeRefreshToken(token)
    }

    override fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication) {
        customJdbcTokenStore.storeRefreshToken(refreshToken, authentication)
    }

    fun deleteAllRefreshTokensByUsername(username: String) =
        customJdbcTokenStore.deleteAllRefreshTokensByUsername(username)


    fun deleteExpiredRefreshTokens() =
        customJdbcTokenStore.deleteExpiredRefreshTokens()

}

@Component
class CustomJdbcTokenStore(
    private val jdbcTemplate: JdbcTemplate,
    dataSource: DataSource) : JdbcTokenStore(dataSource) {

    companion object {
        const val REFRESH_TOKEN_INSERT_STATEMENT = "insert into oauth_refresh_token (token_id, token, authentication, user_name, expiration) values (?, ?, ?, ?, ?)"
        const val REFRESH_TOKEN_DELETE_BY_USERNAME_STATEMENT = "delete from oauth_refresh_token where user_name = ?"
        const val REFRESH_TOKEN_DELETE_EXPIRED_STATEMENT = "delete from oauth_refresh_token where expiration < ?"
    }

    override fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication) {
        val expiringToken = refreshToken as ExpiringOAuth2RefreshToken

        jdbcTemplate.update(REFRESH_TOKEN_INSERT_STATEMENT,
            arrayOf(
                extractTokenKey(refreshToken.value),
                SqlLobValue(serializeRefreshToken(refreshToken)),
                SqlLobValue(serializeAuthentication(authentication)),
                if (authentication.isClientOnly) null else authentication.name,
                expiringToken.expiration.time
            ),
            intArrayOf(Types.VARCHAR, Types.BLOB, Types.BLOB, Types.VARCHAR, Types.BIGINT))
    }

    fun deleteAllRefreshTokensByUsername(username: String): Int {
        return jdbcTemplate.update(REFRESH_TOKEN_DELETE_BY_USERNAME_STATEMENT, username)
    }

    fun deleteExpiredRefreshTokens(): Int {
        return jdbcTemplate.update(
            REFRESH_TOKEN_DELETE_EXPIRED_STATEMENT, arrayOf(
            Instant.now().toEpochMilli()
        ),
            intArrayOf(Types.BIGINT)
        )
    }
}
