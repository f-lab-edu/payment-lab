package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.PaymentResultEvent

class PaymentEventResultSyncRecorder(
    private val logProcessor: AsyncAppenderPaymentTransactionLogProcessor
) {
    fun record(event: PaymentResultEvent) {
        logProcessor.process(event)
    }
}