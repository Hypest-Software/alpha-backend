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
@RequestMapping("$PUBLIC_API_ENDPOINT_V1/snax")
class SnaxController(
    private val userService: UserService,
    private val tokenInfo: TokenInfo
) {
    @GetMapping("/iamnot")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    fun getInfo(): ResponseEntity<String> {
        return ResponseEntity.ok("Przecież ja w ogóle nie mam CSa")
    }
}
