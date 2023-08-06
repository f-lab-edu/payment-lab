package org.collaborators.paymentslab.payment.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class PaymentCompletedEvent(
    val accountId: Long,
    val approvedAt: Date,
    var orderId: String,
    val orderName: String,
    val amount: Int,
    val paymentKey: String,
    val status: String,
    private val occurredOn: Date
): DomainEvent {
    constructor(tossPayments: TossPayments): this(
        tossPayments.accountId!!,
        Date.from(tossPayments.info!!.approvedAt.atZone(ZoneId.systemDefault()).toInstant()),
        tossPayments.info!!.orderId,
        tossPayments.info!!.orderName,
        tossPayments.cardInfo!!.amount,
        tossPayments.info!!.paymentKey,
        tossPayments.status,
        Date()
    )

    override fun occurredOn(): Date {
        return occurredOn
    }
}