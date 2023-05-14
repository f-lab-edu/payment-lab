package org.collaborators.paymentslab.presentation.request

import jakarta.validation.constraints.NotBlank
import org.collaborator.paymentlab.common.KeyGenerator
import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand

class TossPaymentsKeyInRequest(

    val amount: Int,
    @field:NotBlank
    val orderName: String,
    @field:NotBlank
    val cardNumber: String,
    @field:NotBlank
    val cardExpirationYear: String,
    @field:NotBlank
    val cardExpirationMonth: String,
    @field:NotBlank
    val cardPassword: String,
    @field:NotBlank
    val customerIdentityNumber: String
) {
    fun toCommand(): TossPaymentsKeyInPayCommand {
        return TossPaymentsKeyInPayCommand(
            amount = this.amount,
            orderId = KeyGenerator.generate("ord_"),
            orderName = orderName,
            cardNumber = cardNumber,
            cardExpirationYear = cardExpirationYear,
            cardExpirationMonth = cardExpirationMonth,
            cardPassword = cardPassword,
            customerIdentityNumber = customerIdentityNumber
        )
    }
}
