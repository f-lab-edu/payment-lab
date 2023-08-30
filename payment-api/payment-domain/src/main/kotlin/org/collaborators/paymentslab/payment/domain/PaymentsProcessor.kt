package org.collaborators.paymentslab.payment.domain

interface PaymentsProcessor {
    fun process(
        paymentOrderId: Long,
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