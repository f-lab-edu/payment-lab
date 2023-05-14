package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import java.time.LocalDateTime

data class TossPaymentsApprovalResponse(
    val mId: String,
    val lastTransactionKey: String,
    val paymentKey: String,
    val orderId: String,
    val orderName: String,
    val taxExemptionAmount: Int,
    val status: String,
    val requestedAt: LocalDateTime,
    val approvedAt: LocalDateTime,
    val useEscrow: Boolean,
    val cultureExpense: Boolean,
    val secret: String?,
    val type: String,
    val country: String,
    val transactionKey: String?,
    val currency: String,
    val totalAmount: Int,
    val balanceAmount: Int,
    val suppliedAmount: Int,
    val vat: Int,
    val taxFreeAmount: Int,
    val method: String,
    val version: String,
    val card: TossPaymentsCardInfoResponse
)
