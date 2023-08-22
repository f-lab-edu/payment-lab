package org.collaborators.paymentslab.log.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import java.util.*

class PaymentResultEvent(
    val accountId: Long,
    val orderName: String,
    val amount: Int,
    val status: String,
    private val occurredOn: Date
): DomainEvent {
    override fun occurredOn(): Date {
        return occurredOn
    }
}