package org.collaborators.paymentslab.payment.application

import org.collaborators.paymentslab.payment.domain.TossPaymentsProcessor
import org.springframework.stereotype.Service

@Service
class PaymentService(private val tossPaymentsProcessor: TossPaymentsProcessor) {
    fun payed(orderId: String, paymentKey: String, amount: Int) {
        tossPaymentsProcessor.payed(orderId, paymentKey, amount)
    }
}