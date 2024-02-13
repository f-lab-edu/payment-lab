package org.collaborators.paymentslab.payment.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import org.collaborators.paymentslab.payment.domain.entity.TossPayments
import java.util.*

class PaymentResultEvent(
    val accountId: Long,
    val paymentOrderId: Long,
    var orderId: String,
    val orderName: String,
    val amount: Int,
    val paymentKey: String,
    val status: String,
    private val occurredOn: Date,
    private val typeSimpleName: String = PaymentResultEvent::class.simpleName!!
): DomainEvent {

    constructor(tossPayments: TossPayments, paymentOrderId: Long): this(
        tossPayments.accountId!!,
        paymentOrderId,
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

    override fun typeSimpleName(): String {
        return typeSimpleName
    }
}