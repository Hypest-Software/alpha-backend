package dev.hypestsoftware.hackyeah2020.backend.model.dto

class UserDto(
    val userUUID: String,
    val username: String,
    val roles: Set<String>
)
