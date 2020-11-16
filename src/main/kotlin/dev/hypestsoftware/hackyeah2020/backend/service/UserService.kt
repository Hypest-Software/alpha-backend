package dev.hypestsoftware.hackyeah2020.backend.service

import dev.hypestsoftware.hackyeah2020.backend.exception.ApiErrorCode
import dev.hypestsoftware.hackyeah2020.backend.exception.InvalidPasswordException
import dev.hypestsoftware.hackyeah2020.backend.model.Role
import dev.hypestsoftware.hackyeah2020.backend.model.RoleName
import dev.hypestsoftware.hackyeah2020.backend.model.User
import dev.hypestsoftware.hackyeah2020.backend.model.dto.UserCreateDto
import dev.hypestsoftware.hackyeah2020.backend.model.dto.UserUpdateDto
import dev.hypestsoftware.hackyeah2020.backend.repository.RoleRepository
import dev.hypestsoftware.hackyeah2020.backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

interface UserService {
    fun createNewUser(userCreateDto: UserCreateDto): UUID
    fun updateUser(uuid: String, userUpdateDto: UserUpdateDto)
    fun deleteUser(userToDelete: User)
    fun getUserByEmail(email: String): User?
    fun getUserByUUID(uuid: String): User?
    fun userWithGivenEmailExists(email: String): Boolean

    @Throws(InvalidPasswordException::class)
    fun changePassword(username: String, newPassword: String, oldPassword: String)
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Throws(InvalidPasswordException::class)
    override fun changePassword(username: String, newPassword: String, oldPassword: String) {
        val loggedInUser = getUserByEmail(username)!!
        if (passwordEncoder.matches(oldPassword, loggedInUser.password)) {
            loggedInUser.password = passwordEncoder.encode(newPassword)
            userRepository.save(loggedInUser)
        } else {
            ApiErrorCode.AU_0001.throwException()
        }
    }

    override fun createNewUser(userCreateDto: UserCreateDto): UUID {
        val user = userRepository.save(
            User(
                username = userCreateDto.email,
                password = passwordEncoder.encode(userCreateDto.password),
                enabled = true,
                roles = userCreateDto.roles.map { roleName -> Role(name = roleName) }.toMutableSet()
            )
        )

        return user.uuid
    }

    override fun updateUser(uuid: String, userUpdateDto: UserUpdateDto) {
        val oldUser = userRepository.findByUuid(UUID.fromString(uuid))

        if (oldUser != null) {
            val newRolesAsListOfString = userUpdateDto.roles

            val toRemoveRoles = oldUser.roles.filter { !newRolesAsListOfString.contains(it.name) }
            val toAdd = newRolesAsListOfString.filter { oldUser.roles.find { r -> r.name == it } == null }
                .map { Role(name = it) }

            oldUser.roles.removeAll(toRemoveRoles)
            oldUser.roles.addAll(toAdd)

            userRepository.save(oldUser)
        } else {
            ApiErrorCode.AU_0002.throwException()
        }
    }

    override fun deleteUser(userToDelete: User) {
        userRepository.deleteByUsername(userToDelete.username)
    }

    override fun getUserByEmail(email: String): User? {
        return userRepository.findByUsername(email)
    }

    override fun getUserByUUID(uuid: String): User? {
        return userRepository.findByUuid(UUID.fromString(uuid))
    }

    override fun userWithGivenEmailExists(email: String): Boolean {
        val user = getUserByEmail(email)
        return user != null
    }

}
