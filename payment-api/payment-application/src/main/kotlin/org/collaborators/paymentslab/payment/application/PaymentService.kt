package org.collaborators.paymentslab.payment.application

import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.domain.TossPaymentsProcessor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PaymentService(private val tossPaymentsProcessor: TossPaymentsProcessor) {
    fun keyInPay(command: TossPaymentsKeyInPayCommand) {
        tossPaymentsProcessor.keyInPay(
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