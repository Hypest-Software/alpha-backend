package dev.hypestsoftware.hackyeah2020.backend.repository

import dev.hypestsoftware.hackyeah2020.backend.model.OAuthRefreshToken
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface OAuthRefreshTokenRepository : CrudRepository<OAuthRefreshToken, String> {

    @Modifying
    @Transactional
    fun deleteByUsername(username: String): Int

    @Modifying
    @Transactional
    fun deleteByExpirationLessThan(maxExpiration: Long): Int
}
