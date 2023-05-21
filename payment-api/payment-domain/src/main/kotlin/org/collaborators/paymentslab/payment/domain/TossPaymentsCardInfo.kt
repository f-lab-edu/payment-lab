package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class TossPaymentsCardInfo constructor(
    var amount: Int,
    var issuerCode: String,
    var acquirerCode: String? = null,
    var number: String,
    var installmentPlanMonths: Int,
    var approveNo: String,
    var useCardPoint: Boolean,
    var cardType: String,
    var ownerType: String,
    @Enumerated(EnumType.STRING)
    var acquireStatus: TossPaymentsAcquireStatus,
    var isInterestFree: Boolean,
    var interestPayer: String? = null
) {
    enum class TossPaymentsAcquireStatus(private val description: String) {
        READY("매입 대기"),
        REQUESTED("매입 요청됨"),
        COMPLETED("매입 완료"),
        CANCEL_REQUESTED("매입 취소 요청됨"),
        CANCELED("매입 취소 완료");
    }
}