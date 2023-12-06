package org.collaborators.paymentslab.payment.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import java.time.ZoneId
import java.util.Date

class PaymentOrderRecordEvent(
    val id: Long,
    val accountId: Long,
    val status: String,
    private val occurredOn: Date,
    private val typeSimpleName: String = PaymentOrderRecordEvent::class.simpleName!!
): DomainEvent {
    constructor(paymentOrder: PaymentOrder): this(
        paymentOrder.id()!!,
        paymentOrder.accountId,
        paymentOrder.status.name,
        Date.from(paymentOrder.createAt.atZone(ZoneId.systemDefault()).toInstant())
    )

    override fun occurredOn(): Date {
        return occurredOn
    }

    override fun typeSimpleName(): String {
        return typeSimpleName
    }
}