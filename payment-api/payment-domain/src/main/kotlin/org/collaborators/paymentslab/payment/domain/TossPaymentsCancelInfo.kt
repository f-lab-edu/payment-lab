package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class TossPaymentsCancelInfo protected constructor(
    var cancelAmount: Int?,
    var cancelReason: String?,
    var taxFreeAmountCancel: Int?,
    var refundableAmount: Int?,
    var transactionKey: String?,
    var canceledAt: LocalDateTime?,
)