package org.collaborators.paymentslab.presentation.response

import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQueryQueryModel
import java.time.LocalDateTime

data class PaymentHistoryResponse(
    private val orderId: String,
    private val orderName: String,
    private val amount: Int,
    private val status: String,
    private val approvedAt: LocalDateTime
) {
    companion object {
        fun of(data: PaymentHistoryQueryQueryModel): PaymentHistoryResponse {
            return PaymentHistoryResponse(
                data.orderId, data.orderName, data.amount, data.status, data.approvedAt
            )
        }
    }
}
