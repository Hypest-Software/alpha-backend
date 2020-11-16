package dev.hypestsoftware.hackyeah2020.backend.exception

import org.springframework.http.HttpStatus

class UnknownException : RestException("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR)
