package org.collaborators.paymentslab.payment.application

import org.collaborators.paymentslab.payment.application.command.PaymentOrderCommand
import org.collaborators.paymentslab.payment.domain.PaymentOrderProcessor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PaymentOrderService(
    private val paymentOrderProcessor: PaymentOrderProcessor,
) {
    fun generate(command: PaymentOrderCommand) {
        paymentOrderProcessor.process(command.accountId, command.orderName, command.amount)
    }
}