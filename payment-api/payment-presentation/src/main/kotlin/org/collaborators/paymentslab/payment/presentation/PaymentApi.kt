package org.collaborators.paymentslab.payment.presentation

import jakarta.validation.Valid
import org.collaborator.paymentlab.common.result.ApiResult
import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQuery
import org.collaborators.paymentslab.payment.presentation.request.TossPaymentsKeyInRequest
import org.collaborators.paymentslab.payment.presentation.response.PaymentHistoryResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("api/v1/toss-payments")
class PaymentApi(private val paymentService: PaymentService) {

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("key-in/{paymentOrderId}")
    fun keyInPayed(
        @PathVariable paymentOrderId: Long,
        @RequestBody @Valid request: TossPaymentsKeyInRequest
    ) {
        paymentService.keyInPay(paymentOrderId, request.toCommand())
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("payment-order")
    fun generatePaymentOrder(
        @RequestBody @Valid request: PaymentOrderRequest
    ): ResponseEntity<Void> {
        val paymentOrderId = paymentService.generatePaymentOrder()
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI("/api/v1/toss-payments/${paymentOrderId}")).build()
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    fun readPaymentHistories(
        @RequestParam(required = false, defaultValue = "0") pageNum: Int,
        @RequestParam(required = false, defaultValue = "6") pageSize: Int
    ): ApiResult<List<PaymentHistoryResponse>> {
        
        val data = paymentService.readHistoriesFrom(PaymentHistoryQuery.of(pageNum, pageSize))
        val payload = data.map { PaymentHistoryResponse.of(it) }
        return ApiResult.success(payload)
    }
}