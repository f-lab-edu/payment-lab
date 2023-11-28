package org.collaborators.paymentslab.payment.domain.entity

import jakarta.persistence.*
import org.collaborator.paymentlab.common.domain.AbstractAggregateRoot
import org.collaborators.paymentslab.payment.domain.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@DynamicInsert
@DynamicUpdate
class TossPayments protected constructor(
    @Embedded
    var info: TossPaymentsInfo? = null,
    @Embedded
    var cancelInfo: TossPaymentsCancelInfo? = null,
    @Embedded
    var cardInfo: TossPaymentsCardInfo? = null,
    @Column(nullable = false)
    var status: String,
    @Enumerated(EnumType.STRING)
    private var payMethod: PayMethod,
    @CreationTimestamp
    private val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    private val modifiedAt: LocalDateTime? = null
): AbstractAggregateRoot<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var accountId: Long? = null

    constructor(
        info: TossPaymentsInfo,
        cancelInfo: TossPaymentsCancelInfo?,
        cardInfo: TossPaymentsCardInfo?,
        status: String,
        payMethod: PayMethod
    ) : this(info, cancelInfo, cardInfo, status, payMethod, null, null)

    override fun id(): Long {
        return this.id!!
    }

    fun resultOf(accountId: Long, paymentsStatus: PaymentsStatus) {
        this.accountId = accountId
        this.status = paymentsStatus.name
        registerEvent(PaymentResultEvent(this))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TossPayments

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}