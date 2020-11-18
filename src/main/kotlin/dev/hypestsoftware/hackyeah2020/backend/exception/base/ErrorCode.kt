package dev.hypestsoftware.hackyeah2020.backend.exception.base

interface ErrorCode {
    fun id(): String

    @Throws(RestException::class)
    fun throwException(): Nothing
}
