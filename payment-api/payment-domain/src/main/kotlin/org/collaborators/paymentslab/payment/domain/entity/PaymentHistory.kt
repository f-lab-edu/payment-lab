package org.collaborators.paymentslab.payment.domain.entity

import jakarta.persistence.*
import org.collaborator.paymentlab.common.domain.DomainEvent
import org.collaborator.paymentlab.common.error.InvalidArgumentException
import org.collaborators.paymentslab.payment.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.payment.domain.PaymentResultEvent
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
class PaymentHistory protected constructor(
    @Column(nullable = false)
    val accountId: Long? = null,
    @Column(nullable = false)
    val approvedAt: LocalDateTime,
    @Column(nullable = false)
    val orderId: String,
    @Column(nullable = false)
    val orderName: String,
    @Column(nullable = false)
    val amount: Int,
    @Column(nullable = false)
    val paymentKey: String,
    @Column(nullable = false)
    val status: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    companion object {
        private fun newInstanceFrom(event: PaymentOrderRecordEvent): PaymentHistory {
            return PaymentHistory(
                event.accountId,
                event.occurredOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                "",
                "",
                -1,
                "",
                event.status
            )
        }

        private fun newInstanceFrom(event: PaymentResultEvent): PaymentHistory {
            return PaymentHistory(
                event.accountId,
                event.occurredOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                event.orderId,
                event.orderName,
                event.amount,
                event.paymentKey,
                event.status
            )
        }

        fun newInstanceFrom(tossPayments: TossPayments): PaymentHistory {
            return PaymentHistory(
                tossPayments.accountId!!,
                tossPayments.info!!.approvedAt,
                tossPayments.info!!.orderId,
                tossPayments.info!!.orderName,
                tossPayments.cardInfo!!.amount,
                tossPayments.info!!.paymentKey,
                tossPayments.status
            )
        }

        fun newInstanceFrom(accountId: Long,
                            approvedAt: LocalDateTime,
                            orderId: String,
                            orderName: String,
                            amount: Int,
                            paymentKey: String,
                            status: String): PaymentHistory {
            return PaymentHistory(
                accountId,
                approvedAt,
                orderId,
                orderName,
                amount,
                paymentKey,
                status
            )
        }

        fun newInstanceFrom(domainEvent: DomainEvent): PaymentHistory {
           return when(domainEvent) {
                is PaymentOrderRecordEvent -> newInstanceFrom(domainEvent)
                is PaymentResultEvent -> newInstanceFrom(domainEvent)
                else -> throw InvalidArgumentException()
           }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PaymentHistory

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}