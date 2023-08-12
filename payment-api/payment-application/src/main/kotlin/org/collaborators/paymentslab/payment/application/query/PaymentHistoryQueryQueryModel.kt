package org.collaborators.paymentslab.payment.application.query

import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import java.time.LocalDateTime

data class PaymentHistoryQueryQueryModel(
    val accountId: Long,
    val orderId: String,
    val orderName: String,
    val amount: Int,
    val status: String,
    val approvedAt: LocalDateTime
) {
    companion object {
        fun of(entity: PaymentHistory): PaymentHistoryQueryQueryModel {
            return PaymentHistoryQueryQueryModel(
               entity.accountId!!, entity.orderId, entity.orderName, entity.amount, entity.status, entity.approvedAt
            )
        }
    }
}
