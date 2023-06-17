package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.*
import java.time.LocalDateTime

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
        fun newInstanceFrom(tossPayments: TossPayments): PaymentHistory  {
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