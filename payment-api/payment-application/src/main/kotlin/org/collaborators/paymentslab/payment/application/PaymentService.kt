package org.collaborators.paymentslab.payment.application

import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Profile(value = ["local", "default"])
@Transactional
class PaymentService(private val paymentsProcessor: PaymentsProcessor) {
    fun keyInPay(command: TossPaymentsKeyInPayCommand) {
        paymentsProcessor.keyInPay(
            command.amount,
            command.orderId,
            command.orderName,
            command.cardNumber,
            command.cardExpirationYear,
            command.cardExpirationMonth,
            command.cardPassword,
            command.customerIdentityNumber
        )
    }
}