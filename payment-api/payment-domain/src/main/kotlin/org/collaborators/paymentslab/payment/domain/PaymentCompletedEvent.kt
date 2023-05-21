package org.collaborators.paymentslab.payment.domain

import org.collaborator.paymentlab.common.domain.DomainEvent
import java.util.Date

class PaymentCompletedEvent(
    private var orderId: String,
    private val orderName: String,
    private val amount: Int,
    private val occurredOn: Date
): DomainEvent {
    constructor(tossPayments: TossPayments): this(
        tossPayments.info!!.orderId, tossPayments.info!!.orderName, tossPayments.info!!.totalAmount, Date()
    )

    override fun occurredOn(): Date {
        return occurredOn
    }
}