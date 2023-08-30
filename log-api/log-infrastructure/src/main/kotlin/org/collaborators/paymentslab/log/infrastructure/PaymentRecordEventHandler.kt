package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PaymentRecordEventHandler(
    private val eventRecorder: PaymentEventResultSyncRecorder
) {

    @Async
    @EventListener
    fun handle(event: PaymentResultEvent) {
        eventRecorder.record(event)
    }
}