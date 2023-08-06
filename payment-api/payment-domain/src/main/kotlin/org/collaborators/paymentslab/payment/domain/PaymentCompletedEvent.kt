package org.collaborators.paymentslab.payment.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import java.time.LocalDateTime
import java.util.Date

class PaymentCompletedEvent(
    val accountId: Long,
    val approvedAt: LocalDateTime,
    var orderId: String,
    val orderName: String,
    val amount: Int,
    val paymentKey: String,
    val status: String,
    private val occurredOn: Date
): DomainEvent {
    constructor(tossPayments: TossPayments): this(
        tossPayments.accountId!!,
        tossPayments.info!!.approvedAt,
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