package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PaymentRecordEventHandler(
    private val eventRecorder: PaymentEventResultSyncRecorder
) {

    @EventListener
    fun handle(event: PaymentResultEvent) {
        eventRecorder.execute(event)
    }
}