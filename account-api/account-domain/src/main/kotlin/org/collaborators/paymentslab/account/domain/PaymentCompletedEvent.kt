package org.collaborators.paymentslab.account.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import java.util.Date

class PaymentCompletedEvent(
    val accountId: Long,
    var orderId: String,
    val orderName: String,
    val amount: Int,
    private val occurredOn: Date
): DomainEvent {
    override fun occurredOn(): Date {
        return occurredOn
    }
}