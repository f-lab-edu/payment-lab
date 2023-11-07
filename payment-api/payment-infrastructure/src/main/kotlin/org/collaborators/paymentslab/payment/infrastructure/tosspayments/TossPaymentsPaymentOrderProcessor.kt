package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.domain.PaymentOrderProcessor

class TossPaymentsPaymentOrderProcessor(
    private val paymentsTransactionEventPublisher: TossPaymentsTransactionEventPublisher
): PaymentOrderProcessor {

    override fun process(accountId: Long, orderName: String, amount: Int) {
        paymentsTransactionEventPublisher.publishAndRecord(accountId, orderName, amount)
    }
}