package dev.hypestsoftware.hackyeah2020.backend.exception.base

import dev.hypestsoftware.hackyeah2020.backend.exception.rest.AuthException
import org.springframework.http.HttpStatus

enum class ApiErrorCode(private val exception: RestException) : ErrorCode {
    AUTH_0000(RestException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)),
    AUTH_0001(AuthException("Wrong password", HttpStatus.BAD_REQUEST)),
    AUTH_0002(RestException("Error while updating user", HttpStatus.INTERNAL_SERVER_ERROR)),
    AUTH_0003(AuthException("Wrong JWT provded by the client", HttpStatus.BAD_REQUEST)),
    AUTH_0004(AuthException("Error while retrieving logged in user", HttpStatus.INTERNAL_SERVER_ERROR))
    ;

    init {
        exception.code = this
    }

    override fun id(): String = this.name
    override fun throwException(): Nothing {
        throw this.exception
    }
}
