package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class TossPaymentsInfo(
        var version: String,
        var paymentKey: String,
        var type: String,
        var orderId: String,
        var orderName: String,
        var mid: String,
        var currency: String? = "KRW",
        var method: String?,
        var totalAmount: Int,
        var balanceAmount: Int,
        var requestedAt: LocalDateTime,
        var approvedAt: LocalDateTime,
        var useEscrow: Boolean ?= false,
        var lastTransactionKey: String ?= null,
        var suppliedAmount: Int? = null,
        var vat: Int,
        var cultureExpense: Boolean,
        var taxFreeAmount: Int,
        var taxExemptionAmount: Int
)