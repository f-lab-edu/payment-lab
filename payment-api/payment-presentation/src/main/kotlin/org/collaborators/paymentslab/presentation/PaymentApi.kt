package org.collaborators.paymentslab.presentation

import jakarta.validation.Valid
import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.presentation.request.TossPaymentsKeyInRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/toss-payments")
class PaymentApi(private val paymentService: PaymentService) {

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("key-in")
    fun keyInPayed(
        @RequestBody @Valid request: TossPaymentsKeyInRequest
    ) {
        paymentService.keyInPay(request.toCommand())
    }
}