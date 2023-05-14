package org.collaborators.paymentslab.payment.domain

interface TossPaymentsProcessor {
    fun keyInPay(
        amount: Int,
        orderId: String,
        orderName: String,
        cardNumber: String,
        cardExpirationYear: String,
        cardExpirationMonth: String,
        cardPassword: String,
        customerIdentityNumber: String
    )
}