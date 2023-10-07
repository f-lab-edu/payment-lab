package org.collaborators.paymentslab.log.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import java.util.Date

class PaymentOrderRecordEvent(
    val id: Long,
    val accountId: Long,
    val status: String,
    private val occurredOn: Date
): DomainEvent {
    override fun occurredOn(): Date {
        return occurredOn
    }
}