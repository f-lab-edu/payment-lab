package org.collaborators.paymentslab.account.infrastructure

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.InvalidArgumentException
import org.collaborator.paymentlab.common.error.InvalidTokenException
import org.collaborator.paymentlab.common.error.ServiceException
import org.collaborator.paymentlab.common.result.ApiResult
import org.collaborators.paymentslab.account.domain.exception.DuplicatedEmailException
import org.collaborators.paymentslab.account.domain.exception.InvalidAccountException
import org.collaborators.paymentslab.account.domain.exception.PasswordNotMatchedException
import org.collaborators.paymentslab.account.infrastructure.jwt.exception.AlreadyTokenExpiredException
import org.collaborators.paymentslab.account.infrastructure.jwt.exception.RefreshTokenNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["org.collaborators.paymentslab.account"])
class AccountModuleExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(value = [
        AlreadyTokenExpiredException::class,
        RefreshTokenNotFoundException::class,
        InvalidTokenException::class,
        DuplicatedEmailException::class,
        PasswordNotMatchedException::class,
        InvalidAccountException::class,
        InvalidArgumentException::class
    ])
    fun onError(e: ServiceException): ResponseEntity<ApiResult<*>> {
        log.error("ServiceError: ", e)
        if (e.message != null)
            return ResponseEntity
                .status(toStatus(e.errorCode))
                .body(ApiResult.error(e.errorCode, e))
        
        return ResponseEntity
            .status(toStatus(e.errorCode))
            .body(ApiResult.error(e))
    }

    private fun toStatus(errorCode: ErrorCode): HttpStatus {
        return HttpStatus.valueOf(errorCode.status)
    }
}