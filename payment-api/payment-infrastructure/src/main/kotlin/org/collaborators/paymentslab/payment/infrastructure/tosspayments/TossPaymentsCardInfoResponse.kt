package org.collaborators.paymentslab.payment.infrastructure.tosspayments

data class TossPaymentsCardInfoResponse(
    val issuerCode: String,
    val acquirerCode: String,
    val number: String,
    val installmentPlanMonths: Int,
    val isInterestFree: Boolean,
    val approveNo: String,
    val useCardPoint: Boolean,
    val cardType: String,
    val ownerType: String,
    val acquireStatus: String,
    val amount: Int
)
