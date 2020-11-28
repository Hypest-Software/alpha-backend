package dev.hypestsoftware.hackyeah2020.backend.model.dto

import dev.hypestsoftware.hackyeah2020.backend.model.RoleName

data class UserRegisterDto(
    val email: String,
    val password: String
) {
    fun toUserCreateDto() = UserCreateDto(email, password, setOf(RoleName.ROLE_USER))
}
