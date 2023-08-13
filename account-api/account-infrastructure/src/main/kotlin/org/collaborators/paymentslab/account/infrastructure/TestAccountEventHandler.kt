package org.collaborators.paymentslab.account.infrastructure

import org.collaborators.paymentslab.account.domain.PaymentCompletedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class TestAccountEventHandler(private val publisher: ApplicationEventPublisher) {

    @EventListener
    fun handle(event: PaymentCompletedEvent) {
        println("get event from domain event translator")
        println("published event - accountId:${event.accountId}, orderId:${event.orderId}, orderName:${event.orderName}")
    }
}