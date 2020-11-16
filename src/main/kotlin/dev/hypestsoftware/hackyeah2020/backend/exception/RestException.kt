package dev.hypestsoftware.hackyeah2020.backend.exception

import org.springframework.http.HttpStatus

open class RestException (msg: String, val status: HttpStatus) : Exception(msg) {
    lateinit var code: ErrorCode
}
