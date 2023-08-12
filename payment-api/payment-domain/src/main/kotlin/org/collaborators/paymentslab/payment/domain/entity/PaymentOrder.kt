package org.collaborators.paymentslab.payment.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class PaymentOrder(
    @Column(nullable = false)
    val accountId: Long,
    @Column(nullable = false)
    val orderName: String,
    @Column(nullable = false)
    val amount: Int,
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val status: PaymentsStatus,
    @Column(nullable = false)
    val createAt: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}