package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class TossPaymentsInfo {
    private val version: String? = null
    private val orderId: String? = null
    private val orderName: String? = null
    private val paymentKey: String? = null
    private val type: String? = null
    private val mid: String? = null
    private val totalAmount: Int? = null
    private val balanceAmount: Int? = null
    private val suppliedAmount: Int? = null
    private val status: String? = null
    private val requestedAt: LocalDateTime? = null
    private val approvedAt: LocalDateTime? = null
    private val transactionKey: String? = null
    private val vat: Int? = null
    private val taxFreeAmount: Int? = null

    protected constructor()
}