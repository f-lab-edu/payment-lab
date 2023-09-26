package org.collaborators.paymentslab.payment.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import java.time.ZoneId
import java.util.*

class PaymentOrderRecordEvent(
    val accountId: Long,
    val orderName: String,
    val amount: Int,
    val status: String,
    private val occurredOn: Date
): DomainEvent {
    constructor(paymentOrder: PaymentOrder): this(
        paymentOrder.accountId,
        paymentOrder.orderName,
        paymentOrder.amount,
        paymentOrder.status.name,
        Date.from(paymentOrder.createAt.atZone(ZoneId.systemDefault()).toInstant())
    )

    override fun occurredOn(): Date {
        return occurredOn
    }
}