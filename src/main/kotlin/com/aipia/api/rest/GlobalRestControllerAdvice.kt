package com.aipia.api.rest

import com.aipia._common.exception.BusinessException
import com.aipia._common.exception.BusinessExceptionResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

@RestControllerAdvice
class GlobalRestControllerAdvice {

    // 비즈니스 로직 에러
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        e: BusinessException,
        request: HttpServletRequest
    ): ResponseEntity<BusinessExceptionResponse> {
        val response = BusinessExceptionResponse(
            status = e.status,
            message = e.message,
            path = request.requestURI
        )

        return ResponseEntity.status(e.status).body(response)
    }

    // 도메인 에러
    @ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(
        exception: RuntimeException,
        request: HttpServletRequest
    ): ResponseEntity<BusinessExceptionResponse> {
        val status = HttpStatus.BAD_REQUEST
        val message = when (exception) {
            is IllegalArgumentException -> exception.message ?: "올바르지 않은 요청입니다."
            is IllegalStateException -> exception.message ?: "잘못된 요청입니다."
            else -> "처리할 수 없는 요청입니다. 다시 확인해주세요."
        }

        val response = BusinessExceptionResponse(
            status = status.value(),
            message = message,
            path = request.requestURI
        )

        return ResponseEntity.status(status).body(response)
    }

    // DTO 검사 에러
    @ExceptionHandler(value = [MethodArgumentNotValidException::class, HandlerMethodValidationException::class])
    fun handleMethodArgumentNotValidException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<BusinessExceptionResponse?> {
        val status = HttpStatus.BAD_REQUEST
        val message: String = when (exception) {
            is MethodArgumentNotValidException -> exception.message
            is HandlerMethodValidationException -> exception.message
            else -> "잘못된 요청입니다."
        }
        val errors = when (exception) {
            is MethodArgumentNotValidException -> exception.bindingResult.fieldErrors
                .map { error -> "${error.field}:${error.defaultMessage}" }

            is HandlerMethodValidationException -> exception.allErrors
                .map { error ->
                    val paramName = error.codes?.firstOrNull() ?: "unknown"
                    "$paramName:${error.defaultMessage ?: ""}"
                }

            else -> emptyList()
        }

        val response = BusinessExceptionResponse(
            status = status.value(),
            message = message,
            path = request.requestURI,
            details = errors,
        )

        return ResponseEntity.status(status).body(response)
    }

    // 인증 에러
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(
        exception: BadCredentialsException,
        request: HttpServletRequest
    ): ResponseEntity<BusinessExceptionResponse> {
        val status = HttpStatus.UNAUTHORIZED
        val message = exception.message ?: "로그인 정보를 확인할 수 없습니다."

        val response = BusinessExceptionResponse(
            status = status.value(),
            message = message,
            path = request.requestURI
        )

        return ResponseEntity.status(status).body(response)
    }

    // 알 수 없는 에러
    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<BusinessExceptionResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val details = exception.suppressedExceptions
            .map { "${it.javaClass.name}:${it.message}" }

        val response = BusinessExceptionResponse(
            status = status.value(),
            message = "서버 오류가 발생했습니다.",
            path = request.requestURI,
            details = details
        )

        return ResponseEntity.status(status).body(response)
    }

}