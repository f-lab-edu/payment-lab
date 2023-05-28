package org.collaborators.paymentslab.support

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ResourceNotFoundException
import org.collaborator.paymentlab.common.result.ApiResult
import org.collaborators.paymentslab.PaymentsLabApplication
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.nio.charset.StandardCharsets
import java.sql.SQLException
import java.util.stream.Collectors

@RestController
@RestControllerAdvice(basePackageClasses = [PaymentsLabApplication::class])
class GlobalExceptionHandler(private val objectMapper: ObjectMapper): ErrorController {
    private val SERVLET_ERROR_CODE = "jakarta.servlet.error.status_code"
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @RequestMapping("/error")
    fun onError(req: HttpServletRequest): ResponseEntity<ApiResult<*>> {
        log.error(
            "Unhandled Servlet Exception : {}",
            req.requestURI + "?" + req.queryString
        )
        val errorCode = req.getAttribute(SERVLET_ERROR_CODE) as Int
        return ResponseEntity.status(HttpStatus.valueOf(errorCode))
            .body(ApiResult.error(ErrorCode.UN_HANDLED))
    }

    @ExceptionHandler(TossPaymentsApiClientException::class)
    fun onError(exception: TossPaymentsApiClientException): ResponseEntity<ApiResult<*>> {
        log.debug("TossPaymentsApiClientException: {}", exception.message)
        val extractedFromMessage = String(exception.responseBodyAsByteArray, StandardCharsets.UTF_8)
        val errorBody = objectMapper.readValue(extractedFromMessage, ApiResult.Companion.ErrorBody::class.java)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResult.error(errorBody))
    }

    @ExceptionHandler(SQLException::class)
    fun onError(exception: SQLException): ResponseEntity<ApiResult<*>> {
        log.debug("SQLException: {}", exception.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResult.Companion.error(ErrorCode.UN_DEFINED_ERROR))
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun onError(exception: ResourceNotFoundException): ResponseEntity<ApiResult<*>> {
       log.debug("ResourceNotFoundException: {}", exception.message)
        return ResponseEntity.status(exception.errorCode.status)
            .body(ApiResult.error(exception.errorCode))
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun onError(
        exception: HttpRequestMethodNotSupportedException
    ): ResponseEntity<ApiResult<ApiResult.Companion.ErrorBody>> {
        log.debug("HttpRequestMethodNotSupportedException: {}", exception.message)
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ApiResult.error(ErrorCode.METHOD_NOT_ALLOWED))
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun onError(
        exception: HttpMediaTypeNotSupportedException
    ): ResponseEntity<ApiResult<ApiResult.Companion.ErrorBody>> {
        log.debug("HttpMediaTypeNotSupportedException: {}", exception.message)
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(ApiResult.error(ErrorCode.UNSUPPORTED_MEDIA_TYPE))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onError(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<ApiResult<ApiResult.Companion.ErrorBody>> {
        log.error(
            "MethodArgumentNotValidException: {}",
            loggingField(exception.bindingResult)
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResult.error(ErrorCode.INVALID_INPUT))
    }

    private fun loggingField(bindingResult: BindingResult): String? {
        return bindingResult.fieldErrors.stream().map { f: FieldError ->
            this.format(
                f
            )
        }.collect(Collectors.joining("  "))
    }

    private fun format(f: FieldError): String? {
        return if (f.field == "password") {
            "Field : [" + f.field + "] " + "Reason: [" + f.defaultMessage + "]"
        } else "Field : [" + f.field + "] " + "Value: [" + f.rejectedValue + "]"
    }
}