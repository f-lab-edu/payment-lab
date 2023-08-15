package org.collaborators.paymentslab.payment.infrastructure

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException
import org.collaborator.paymentlab.common.result.ApiResult
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["org.collaborators.paymentslab.payment"])
class PaymentModuleExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(value = [
        PaymentOrderNotFoundException::class,
        InvalidPaymentOrderException::class,
        InvalidPaymentOrderAccountIdException::class,
        AlreadyCanceledPaymentOrderException::class,
        AlreadyAbortedPaymentOrderException::class,
        AlreadyDonePaymentOrderException::class,
        AlreadyInProgressPaymentOrderException::class
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