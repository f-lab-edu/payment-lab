package org.collaborators.paymentslab.support

import org.collaborators.paymentslab.payment.domain.PaymentCompletedEvent
import org.collaborators.paymentslab.payment.domain.PaymentRecordEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DomainEventTranslator(private val publisher: ApplicationEventPublisher) {

    @EventListener
    fun translate(event: PaymentRecordEvent) {
        publisher.publishEvent(
            org.collaborators.paymentslab.log.domain.PaymentRecordEvent(
                event.accountId, event.orderName, event.amount, event.status, event.occurredOn()
            )
        )
    }
}