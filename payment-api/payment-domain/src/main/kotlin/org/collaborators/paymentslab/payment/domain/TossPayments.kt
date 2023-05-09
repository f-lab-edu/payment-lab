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
class TossPayments protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Embedded
    var info: TossPaymentsInfo,
    @Embedded
    var cancelInfo: TossPaymentsCancelInfo? = null,
    @Embedded
    var cardInfo: TossPaymentsCardInfo? = null,
    @Enumerated(EnumType.STRING)
    private var payMethod: PayMethod,
    @CreationTimestamp
    private val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    private val modifiedAt: LocalDateTime? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TossPayments

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode() ?: 0
    }


}