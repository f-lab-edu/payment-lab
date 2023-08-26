package org.collaborators.paymentslab.log.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PaymentRecordEventHandler(
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @EventListener
    fun handle(event: PaymentResultEvent) {
        log.info("listening paymentRecord before validation -> accountId: {}, orderName: {}, amount: {}, status: {}",
            event.accountId, event.orderName, event.amount, event.status
        )
        objectMapper.writeValueAsString(event)
    }
}