package org.collaborators.paymentslab.payment.domain.entity

import jakarta.persistence.*
import org.collaborator.paymentlab.common.domain.AbstractAggregateRoot
import org.collaborators.paymentslab.payment.domain.PaymentRecordEvent
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
): AbstractAggregateRoot() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun aborted() {
        this.status = PaymentsStatus.ABORTED
    }

    fun inProcess() {
        this.status = PaymentsStatus.IN_PROGRESS
    }

    fun complete() {
        this.status = PaymentsStatus.DONE
    }

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