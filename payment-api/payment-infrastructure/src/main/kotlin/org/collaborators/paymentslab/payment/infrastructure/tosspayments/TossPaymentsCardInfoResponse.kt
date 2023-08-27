package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.domain.TossPaymentsCardInfo

data class TossPaymentsCardInfoResponse(
    val issuerCode: String = "",
    val acquirerCode: String = "",
    val number: String = "",
    val installmentPlanMonths: Int = -1,
    val isInterestFree: Boolean = false,
    val approveNo: String = "",
    val useCardPoint: Boolean = false,
    val cardType: String = "",
    val ownerType: String = "",
    val acquireStatus: String = TossPaymentsCardInfo.TossPaymentsAcquireStatus.READY.name,
    val amount: Int
) {
    companion object {
        fun preResponse(amount: Int): TossPaymentsCardInfoResponse {
            return TossPaymentsCardInfoResponse(amount = amount)
        }
    }
}
