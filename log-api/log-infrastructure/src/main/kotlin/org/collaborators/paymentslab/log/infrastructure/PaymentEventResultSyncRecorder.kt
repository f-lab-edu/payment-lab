package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.EventResultRecorder
import org.collaborators.paymentslab.log.domain.PaymentResultEvent

class PaymentEventResultSyncRecorder(
    private val logProcessor: FileSystemPaymentCustomLogProcessor
): EventResultRecorder<PaymentResultEvent> {
    override fun execute(event: PaymentResultEvent) {
        logProcessor.execute(event)
    }
}