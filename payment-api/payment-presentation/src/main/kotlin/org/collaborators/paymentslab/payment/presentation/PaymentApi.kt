package org.collaborators.paymentslab.payment.presentation

import jakarta.validation.Valid
import org.collaborator.paymentlab.common.V1_TOSS_PAYMENTS
import org.collaborator.paymentlab.common.result.ApiResult
import org.collaborators.paymentslab.payment.application.PaymentOrderService
import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQuery
import org.collaborators.paymentslab.payment.presentation.request.PaymentOrderRequest
import org.collaborators.paymentslab.payment.presentation.request.TossPaymentsKeyInRequest
import org.collaborators.paymentslab.payment.presentation.response.PaymentHistoryResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping(V1_TOSS_PAYMENTS)
class PaymentApi(
    private val paymentService: PaymentService,
    private val paymentOrderService: PaymentOrderService
    ) {

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
        paymentOrderService.generate(request.toCommand())

        return ResponseEntity.ok().build()
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