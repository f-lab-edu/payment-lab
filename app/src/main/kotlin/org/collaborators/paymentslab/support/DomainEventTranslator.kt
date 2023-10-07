package org.collaborators.paymentslab.support

import org.collaborators.paymentslab.payment.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.payment.domain.PaymentResultEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DomainEventTranslator(private val publisher: ApplicationEventPublisher) {

    @EventListener
    fun translate(event: PaymentResultEvent) {
        publisher.publishEvent(
            org.collaborators.paymentslab.log.domain.PaymentResultEvent(
                event.accountId, event.orderName, event.amount, event.status, event.occurredOn()
            )
        )
    }

    @EventListener
    fun translate(event: PaymentOrderRecordEvent) {
        publisher.publishEvent(
            org.collaborators.paymentslab.log.domain.PaymentOrderRecordEvent(
               event.id, event.accountId, event.status, event.occurredOn()
            )
        )
    }
}