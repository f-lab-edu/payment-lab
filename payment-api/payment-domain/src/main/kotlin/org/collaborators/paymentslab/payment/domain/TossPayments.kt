package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@DynamicInsert
@DynamicUpdate
class TossPayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @Embedded
    val tossPaymentsInfo: TossPaymentsInfo? = null
    @Embedded
    val tossPaymentsCancelInfo: TossPaymentsCancelInfo? = null
    @Embedded
    val tossPaymentsCardInfo: TossPaymentsCardInfo? = null

    @Enumerated(EnumType.STRING)
    private val payMethod: PayMethod? = null

    @CreationTimestamp
    private val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    private val modifiedAt: LocalDateTime? = null

    protected constructor()
}