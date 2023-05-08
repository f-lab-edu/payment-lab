package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class TossPaymentsCancelInfo {
    private var cancelAmount: Int? = null
    private var refundableAmount: Int? = null
    private var cancelReason: String? = null
    private var taxFreeAmountCancel: Int? = null
    private var canceledAt: LocalDateTime? = null

    protected constructor()

    fun TossPaymentsCancelInfo(
        cancelAmount: Int?, refundableAmount: Int?,
        cancelReason: String?, taxFreeAmountCancel: Int?, canceledAt: LocalDateTime?
    ) {
        this.cancelAmount = cancelAmount
        this.refundableAmount = refundableAmount
        this.cancelReason = cancelReason
        this.taxFreeAmountCancel = taxFreeAmountCancel
        this.canceledAt = canceledAt
    }
}