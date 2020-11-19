package dev.hypestsoftware.hackyeah2020.backend.config

import dev.hypestsoftware.hackyeah2020.backend.model.RoleName
import dev.hypestsoftware.hackyeah2020.backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import java.security.KeyPair
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(
    private val dataSource: DataSource,
    private val jdbcTemplate: JdbcTemplate
) : AuthorizationServerConfigurerAdapter() {

    @Value("\${app.oauth.webapp.client-id}")
    private lateinit var webappClientId: String

    @Value("\${app.oauth.webapp.client-secret}")
    private lateinit var webappClientSecret: String

    @Value("\${app.keystore.file}")
    private lateinit var keyStore: Resource

    @Value("\${app.keystore.password}")
    private lateinit var keyStorePassword: String

    @Value("\${app.keystore.keypair.alias}")
    private lateinit var keyPairAlias: String

    @Value("\${app.keystore.keypair.password}")
    private lateinit var keyPairPassword: String

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var customUserDetailsService: UserDetailsService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Bean
    fun tokenServices(clientDetailsService: ClientDetailsService): DefaultTokenServices {
        val tokenServices = DefaultTokenServices()
        tokenServices.setSupportRefreshToken(true)
        tokenServices.setTokenStore(tokenStore())
        tokenServices.setClientDetailsService(clientDetailsService)
        tokenServices.setAuthenticationManager(authenticationManager)
        return tokenServices
    }

    @Bean
    fun tokenStore(): TokenStore {
        val customJdbcTokenStore = CustomJdbcTokenStore(dataSource)

        return CustomJwtTokenStore(customJdbcTokenStore, jwtAccessTokenConverter())
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val keyPair = getKeyPair(getKeyStoreKeyFactory())

        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setKeyPair(keyPair)

        return jwtAccessTokenConverter
    }

    @Bean
    fun tokenEnhancer(): TokenEnhancer {
        return CustomTokenConverter(userService)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient(webappClientId)
            .secret(webappClientSecret)
            .scopes("read", "write")
            .authorizedGrantTypes("password", "refresh_token")
            .authorities(*RoleName.values().map { it.name }.toTypedArray())
            .accessTokenValiditySeconds(60 * 15) // 15 minutes
            .refreshTokenValiditySeconds(24 * 60 * 60) // 1 day
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(listOf(tokenEnhancer(), jwtAccessTokenConverter()))

        endpoints
            .authenticationManager(authenticationManager)
            .tokenEnhancer(tokenEnhancerChain)
            .reuseRefreshTokens(false)
            .userDetailsService(customUserDetailsService)
            .tokenStore(tokenStore())
    }

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer
            .passwordEncoder(passwordEncoder)
            .checkTokenAccess("isAuthenticated()")
    }

    private fun getKeyPair(keyStoreKeyFactory: KeyStoreKeyFactory): KeyPair {
        return keyStoreKeyFactory.getKeyPair(keyPairAlias, keyPairPassword.toCharArray())
    }

    fun getKeyStoreKeyFactory(): KeyStoreKeyFactory {
        return KeyStoreKeyFactory(keyStore, keyStorePassword.toCharArray())
    }
}
