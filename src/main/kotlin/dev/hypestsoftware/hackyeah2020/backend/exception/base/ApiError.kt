package dev.hypestsoftware.hackyeah2020.backend.exception.base

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ApiError(
    val status: HttpStatus,
    val message: String?,
    val code: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now(),
    @JsonIgnore
    val internalErrorCode: ErrorCode
) {
    constructor(ex: RestException) : this(
        status = ex.status,
        message = ex.message,
        code = ex.code.id(),
        internalErrorCode = ex.code
    )

    constructor(status: HttpStatus, message: String?, code: ApiErrorCode) : this(
        status = status,
        message = message,
        code = code.id(),
        internalErrorCode = code
    )
}
