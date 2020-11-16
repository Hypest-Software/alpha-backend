package dev.hypestsoftware.hackyeah2020.backend.security

import dev.hypestsoftware.hackyeah2020.backend.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findActiveByUsername(username)
            ?: throw UsernameNotFoundException("User with username $username not found")

        val roles = user.roles

        return UserPrincipal.create(user, roles)
    }
}
