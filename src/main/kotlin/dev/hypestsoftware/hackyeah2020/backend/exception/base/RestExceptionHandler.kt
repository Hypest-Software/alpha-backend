package dev.hypestsoftware.hackyeah2020.backend.exception.base

import dev.hypestsoftware.hackyeah2020.backend.exception.rest.UnknownException
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.stereotype.Component
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
abstract class RestExceptionHandler :
    ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(RestExceptionHandler::class.java)

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return handleApiException(ex, request)
    }

    fun handleApiException(ex: Exception): ResponseEntity<Any> {
        return handleApiException(ex, null)
    }

    @ExceptionHandler(Exception::class)
    fun handleApiException(ex: Exception, request: WebRequest?): ResponseEntity<Any> {
        val apiError: ApiError = when (ex) {
            is RestException -> {
                ApiError(ex)
            }
            is AccessDeniedException -> {
                ApiError(HttpStatus.FORBIDDEN, "Access denied", ApiErrorCode.AUTH_0000)
            }
            is HttpRequestMethodNotSupportedException -> {
                ApiError(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed", ApiErrorCode.AUTH_0000)
            }
            is HttpMediaTypeNotSupportedException -> {
                ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported media type", ApiErrorCode.AUTH_0000)
            }
            is HttpMediaTypeNotAcceptableException -> {
                ApiError(HttpStatus.NOT_ACCEPTABLE, "Media type not acceptable", ApiErrorCode.AUTH_0000)
            }
            is MissingPathVariableException -> {
                ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Missing path variable", ApiErrorCode.AUTH_0000)
            }
            is MissingServletRequestParameterException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Missing request parameter", ApiErrorCode.AUTH_0000)
            }
            is ServletRequestBindingException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Invalid request", ApiErrorCode.AUTH_0000)
            }
            is TypeMismatchException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Invalid request", ApiErrorCode.AUTH_0000)
            }
            is HttpMessageNotReadableException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Invalid request", ApiErrorCode.AUTH_0000)
            }
            is MethodArgumentNotValidException -> {
                var additionalMessage = ""
                if (ex.bindingResult.hasErrors() && ex.bindingResult.errorCount > 0) {
                    val errors = ex.bindingResult.allErrors.joinToString(", ") { it.defaultMessage ?: "" }
                    additionalMessage = ": $errors"
                }
                ApiError(HttpStatus.BAD_REQUEST, "Invalid request$additionalMessage", ApiErrorCode.AUTH_0000)
            }
            is MissingServletRequestPartException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Invalid request", ApiErrorCode.AUTH_0000)
            }
            is BindException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Invalid request", ApiErrorCode.AUTH_0000)
            }
            is NoHandlerFoundException -> {
                ApiError(HttpStatus.NOT_FOUND, "Not found", ApiErrorCode.AUTH_0000)
            }
            is AsyncRequestTimeoutException -> {
                ApiError(HttpStatus.SERVICE_UNAVAILABLE, "Request timeout", ApiErrorCode.AUTH_0000)
            }
            is InsufficientAuthenticationException -> {
                ApiError(
                    HttpStatus.UNAUTHORIZED,
                    "Full authentication is required to access this resource",
                    ApiErrorCode.AUTH_0000
                )
            }
            is BadCredentialsException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Invalid credentials", ApiErrorCode.AUTH_0000)
            }
            is InvalidGrantException -> {
                ApiError(HttpStatus.BAD_REQUEST, "Invalid credentials", ApiErrorCode.AUTH_0000)
            }
            is HttpClientErrorException.BadRequest -> {
                ApiError(HttpStatus.BAD_REQUEST, ex.statusText, ApiErrorCode.AUTH_0000)
            }
            else -> {
                var cause = ex.cause
                while (cause != null && cause !is RestException) {
                    cause = cause.cause
                }
                if (cause != null) {
                    ApiError(cause as RestException)
                } else {
                    ApiError(UnknownException())
                }
            }
        }
        log.error("Exception during request.", ex)
        return ResponseEntity(apiError, apiError.status)
    }
}
