package org.collaborators.paymentslab.support

import org.collaborators.paymentslab.payment.domain.PaymentCompletedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener

//@Component
class DomainEventTranslator(private val publisher: ApplicationEventPublisher) {

    @EventListener
    fun translate(event: PaymentCompletedEvent) {
        println("hello from domain event translator")
        publisher.publishEvent(
            org.collaborators.paymentslab.account.domain.PaymentCompletedEvent(
                event.accountId, event.orderId, event.orderName, event.amount, event.occurredOn()
            )
        )
    }
}