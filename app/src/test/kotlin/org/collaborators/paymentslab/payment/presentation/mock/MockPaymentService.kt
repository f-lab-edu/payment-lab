package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.domain.TossPaymentsProcessor
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Profile(value = ["test"])
@Transactional
class MockPaymentService(private val tossPaymentsProcessor: TossPaymentsProcessor): PaymentService(tossPaymentsProcessor) {
    override fun keyInPay(command: TossPaymentsKeyInPayCommand) {}
}