package dev.hypestsoftware.hackyeah2020.backend.exception

import org.springframework.http.HttpStatus

class InvalidPasswordException(msg: String) : RestException(msg, HttpStatus.BAD_REQUEST)