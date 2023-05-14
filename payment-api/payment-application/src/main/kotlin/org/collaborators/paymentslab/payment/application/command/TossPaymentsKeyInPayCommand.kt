package org.collaborators.paymentslab.payment.application.command

data class TossPaymentsKeyInPayCommand(
    val amount: Int,
    val orderId: String,
    val orderName: String,
    val cardNumber: String,
    val cardExpirationYear: String,
    val cardExpirationMonth: String,
    val cardPassword: String,
    val customerIdentityNumber: String
)
