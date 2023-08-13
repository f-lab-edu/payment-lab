package org.collaborators.paymentslab.payment.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class PaymentOrder protected constructor(
    @Column(nullable = false)
    val accountId: Long,
    @Column(nullable = false)
    val orderName: String,
    @Column(nullable = false)
    val amount: Int,
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var status: PaymentsStatus,
    @Column(nullable = false)
    val createAt: LocalDateTime,
) {
    fun complete() {
        this.status = PaymentsStatus.DONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    companion object {
        fun newInstance(
            accountId: Long,
            orderName: String,
            amount: Int,
            status: PaymentsStatus = PaymentsStatus.READY): PaymentOrder {

            return PaymentOrder(accountId, orderName, amount, status, LocalDateTime.now())
        }
    }
}