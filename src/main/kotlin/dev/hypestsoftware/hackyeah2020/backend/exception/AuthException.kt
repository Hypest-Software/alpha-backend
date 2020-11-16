package dev.hypestsoftware.hackyeah2020.backend.exception

import org.springframework.http.HttpStatus

class AuthException(msg: String, status: HttpStatus) : RestException(msg, status)
