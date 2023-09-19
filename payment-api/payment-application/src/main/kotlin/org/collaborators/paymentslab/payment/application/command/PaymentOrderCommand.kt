package org.collaborators.paymentslab.payment.application.command

data class PaymentOrderCommand(
    val orderName: String,
    val amount: Int
)
