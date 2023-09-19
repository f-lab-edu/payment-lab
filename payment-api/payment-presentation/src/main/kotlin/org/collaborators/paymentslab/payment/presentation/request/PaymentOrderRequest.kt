package org.collaborators.paymentslab.payment.presentation.request

import org.collaborators.paymentslab.payment.application.command.PaymentOrderCommand

data class PaymentOrderRequest(
    val orderName: String,
    val amount: Int
) {
    fun toCommand(): PaymentOrderCommand {
        return PaymentOrderCommand(orderName, amount)
    }
}
