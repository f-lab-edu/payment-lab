package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.PaymentRecordEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PaymentRecordEventHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @EventListener
    fun handle(event: PaymentRecordEvent) {
        log.info("listening paymentRecord before validation -> accountId: {}, orderName: {}, amount: {}, status: {}",
            event.accountId, event.orderName, event.amount, event.status
        )
    }
}