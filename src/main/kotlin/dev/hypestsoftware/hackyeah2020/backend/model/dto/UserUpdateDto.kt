package dev.hypestsoftware.hackyeah2020.backend.model.dto

import dev.hypestsoftware.hackyeah2020.backend.model.RoleName

class UserUpdateDto(
    val roles: Set<RoleName>
)
