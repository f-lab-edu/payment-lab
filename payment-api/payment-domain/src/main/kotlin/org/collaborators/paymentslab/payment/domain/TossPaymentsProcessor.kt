package org.collaborators.paymentslab.payment.domain

interface TossPaymentsProcessor {
    fun payed(orderId: String, paymentKey: String, amount: Int)
}