package org.collaborators.paymentslab.payment.application.command

data class PaymentOrderCommand(
    val accountId: Long,
    val orderName: String,
    val amount: Int
)
