package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PaymentRecordEventHandler(
    private val logProcessor: AsyncAppenderPaymentTransactionLogProcessor
) {

    @Async
    @EventListener
    fun handle(event: PaymentResultEvent) {
        logProcessor.process(event)
    }

    @Async
    @EventListener
    fun handle(event: PaymentOrderRecordEvent) {
        logProcessor.process(event)
    }
}