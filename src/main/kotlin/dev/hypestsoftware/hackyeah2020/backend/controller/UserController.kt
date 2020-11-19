package dev.hypestsoftware.hackyeah2020.backend.controller

import dev.hypestsoftware.hackyeah2020.backend.exception.base.ApiErrorCode
import dev.hypestsoftware.hackyeah2020.backend.model.User
import dev.hypestsoftware.hackyeah2020.backend.model.dto.CurrentUserDto
import dev.hypestsoftware.hackyeah2020.backend.service.UserService
import dev.hypestsoftware.hackyeah2020.backend.utils.PUBLIC_API_ENDPOINT_V1
import dev.hypestsoftware.hackyeah2020.backend.utils.TokenInfo
import dev.hypestsoftware.hackyeah2020.backend.utils.ValidationUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$PUBLIC_API_ENDPOINT_V1/users")
class UserController(
    private val userService: UserService,
    private val tokenInfo: TokenInfo
) {
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    fun getMe(): ResponseEntity<CurrentUserDto> {
        val uuid = tokenInfo.userUUID ?: ApiErrorCode.AUTH_0003.throwException()
        if (!ValidationUtils.isStringUUID(uuid)) {
            ApiErrorCode.AUTH_0003.throwException()
        }

        val user: User = userService.getUserByUUID(uuid) ?: ApiErrorCode.AUTH_0004.throwException()

        return ResponseEntity.ok(
            CurrentUserDto(
                username = user.username,
                roles = user.roles.map { it.name.name }.toSet(),
                userUUID = user.uuid.toString()
            )
        )
    }
}