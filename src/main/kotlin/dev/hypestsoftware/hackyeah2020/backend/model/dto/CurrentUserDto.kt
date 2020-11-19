package dev.hypestsoftware.hackyeah2020.backend.model.dto

class CurrentUserDto(
    val username: String,
    val userUUID: String,
    val roles: Set<String>
)
