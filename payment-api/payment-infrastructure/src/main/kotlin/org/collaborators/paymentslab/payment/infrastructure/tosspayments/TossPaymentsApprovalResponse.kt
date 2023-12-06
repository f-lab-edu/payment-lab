package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import java.time.LocalDateTime

data class TossPaymentsApprovalResponse(
    val mId: String = "",
    val lastTransactionKey: String = "",
    val paymentKey: String = "",
    val orderId: String = "",
    val orderName: String = "",
    val taxExemptionAmount: Int = -1,
    val status: String = "",
    val requestedAt: LocalDateTime = LocalDateTime.now(),
    val approvedAt: LocalDateTime = LocalDateTime.now(),
    val useEscrow: Boolean = false,
    val cultureExpense: Boolean = false,
    val secret: String? = "",
    val type: String = "",
    val country: String = "",
    val transactionKey: String? = "",
    val currency: String = "",
    val totalAmount: Int = -1,
    val balanceAmount: Int = -1,
    val suppliedAmount: Int = -1,
    val vat: Int = -1,
    val taxFreeAmount: Int = -1,
    val method: String = "",
    val version: String = "",
    val card: TossPaymentsCardInfoResponse? = null
) {
    companion object {
        fun preResponseOf(paymentOrder: PaymentOrder, dto: TossPaymentsKeyInDto): TossPaymentsApprovalResponse {
            return TossPaymentsApprovalResponse(
                orderId = dto.orderId,
                orderName = paymentOrder.orderName,
                status = paymentOrder.status.name,
                card = TossPaymentsCardInfoResponse.preResponse(amount = dto.amount)
            )
        }
    }
}
