package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.EventResultRecorder
import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.collaborators.paymentslab.log.domain.TransactionLogProcessor

class PaymentEventResultSyncRecorder(
    private val logProcessor: TransactionLogProcessor<PaymentResultEvent>
): EventResultRecorder<PaymentResultEvent> {
    override fun record(event: PaymentResultEvent) {
        logProcessor.process(event)
    }
}