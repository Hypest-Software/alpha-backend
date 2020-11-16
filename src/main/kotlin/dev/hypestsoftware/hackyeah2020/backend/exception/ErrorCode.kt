package dev.hypestsoftware.hackyeah2020.backend.exception

interface ErrorCode {
    fun id(): String
    fun module(): String

    @Throws(RestException::class)
    fun throwException(): Nothing
}
