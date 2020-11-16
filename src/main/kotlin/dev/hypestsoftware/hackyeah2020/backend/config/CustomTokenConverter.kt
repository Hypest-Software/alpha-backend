package dev.hypestsoftware.hackyeah2020.backend.config

import dev.hypestsoftware.hackyeah2020.backend.security.UserPrincipal
import dev.hypestsoftware.hackyeah2020.backend.service.UserService
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

class CustomTokenConverter(private val userService: UserService) :
    JwtAccessTokenConverter() {

    override fun enhance(accessToken: OAuth2AccessToken?, authentication: OAuth2Authentication?): OAuth2AccessToken? {
        val additionalInfo = HashMap<String, Any>()

        if (authentication!!.oAuth2Request.clientId == "webapp") {
            val principal = authentication.principal as UserPrincipal
            val user = userService.getUserByEmail(principal.username)

            if (user != null) {
                val userUUID = user.uuid

                additionalInfo["user-uuid"] = userUUID.toString()
            }
        }

        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }
}
