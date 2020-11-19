package dev.hypestsoftware.hackyeah2020.backend.exception.rest

import dev.hypestsoftware.hackyeah2020.backend.exception.base.RestException
import org.springframework.http.HttpStatus

class AuthException(msg: String, status: HttpStatus) : RestException(msg, status)
