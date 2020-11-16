package dev.hypestsoftware.hackyeah2020.backend.repository

import dev.hypestsoftware.hackyeah2020.backend.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface UserRepository : CrudRepository<User, UUID> {
    @Query("from User u where u.enabled=true and u.username=:username")
    fun findActiveByUsername(@Param("username") username: String): User?

    fun findByUsername(username: String): User?

    fun findByUuid(uuid: UUID): User?

    @Transactional
    fun deleteByUsername(username: String)
}
