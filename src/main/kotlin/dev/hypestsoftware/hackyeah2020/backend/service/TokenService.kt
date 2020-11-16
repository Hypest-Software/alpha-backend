package dev.hypestsoftware.hackyeah2020.backend.service

import dev.hypestsoftware.hackyeah2020.backend.config.CustomJwtTokenStore
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val customJwtTokenStore: CustomJwtTokenStore
) {

    companion object {
        private val log = LoggerFactory.getLogger(TokenService::class.java)
    }

    fun deleteAllRefreshTokensByUsername(username: String) {
        val deletedTokensCount = customJwtTokenStore.deleteAllRefreshTokensByUsername(username)

        log.info("Deleted [$deletedTokensCount] refresh tokens by user: [$username]")
    }

    @Scheduled(cron = "\${app.scheduler.cron.refresh-token-cleanup}")
    fun scheduleExpiredTokensCleaning() {
        val deletedTokensCount = customJwtTokenStore.deleteExpiredRefreshTokens()

        log.info("Deleted [$deletedTokensCount] expired refresh tokens by scheduled task")
    }
}