package dev.hypestsoftware.hackyeah2020.backend.model.dto

import dev.hypestsoftware.hackyeah2020.backend.model.RoleName

class UserCreateDto(
    val email: String,
    val password: String,
    val roles: Set<RoleName>,
)
