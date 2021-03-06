package dev.hypestsoftware.hackyeah2020.backend.security

import dev.hypestsoftware.hackyeah2020.backend.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class UserPrincipal(
    val id: UUID,
    private val username: String,
    private val password: String,
    private val enabled: Boolean,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun isCredentialsNonExpired(): Boolean {
        return enabled
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun isAccountNonExpired(): Boolean {
        return enabled
    }

    override fun isAccountNonLocked(): Boolean {
        return enabled
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    companion object {
        fun create(user: User): UserPrincipal {
            val authorities = user.roles.map { SimpleGrantedAuthority(it.name.name) }
            return UserPrincipal(user.uuid, user.username, user.password, user.enabled, authorities)
        }
    }
}
