package org.collaborators.paymentslab.payment.presentation.request

import org.collaborators.paymentslab.payment.application.command.PaymentOrderCommand

data class PaymentOrderRequest(
    val accountId: Long,
    val orderName: String,
    val amount: Int
) {
    fun toCommand(): PaymentOrderCommand {
        return PaymentOrderCommand(accountId, orderName, amount)
    }
}
