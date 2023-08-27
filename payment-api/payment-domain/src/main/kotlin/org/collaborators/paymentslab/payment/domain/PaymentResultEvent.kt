package org.collaborators.paymentslab.payment.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.entity.TossPayments
import java.time.ZoneId
import java.util.Date

class PaymentResultEvent(
    val accountId: Long,
    var orderId: String,
    val orderName: String,
    val amount: Int,
    val paymentKey: String,
    val status: String,
    private val occurredOn: Date
): DomainEvent {

    constructor(tossPayments: TossPayments): this(
        tossPayments.accountId!!,
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