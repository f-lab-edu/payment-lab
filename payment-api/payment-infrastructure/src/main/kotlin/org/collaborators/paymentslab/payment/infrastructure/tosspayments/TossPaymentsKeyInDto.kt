package org.collaborators.paymentslab.payment.infrastructure.tosspayments

data class TossPaymentsKeyInDto(
    val amount: Int,
    val orderId: String,
    val orderName: String,
    val cardNumber: String,
    val cardExpirationYear: String,
    val cardExpirationMonth: String,
    val cardPassword: String,
    val customerIdentityNumber: String
)