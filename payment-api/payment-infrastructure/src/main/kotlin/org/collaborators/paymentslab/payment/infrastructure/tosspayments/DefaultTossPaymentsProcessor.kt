package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.domain.TossPaymentsProcessor

class DefaultTossPaymentsProcessor(
    private val tossPaymentsKeyInApprovalProcessor: TossPaymentsKeyInApprovalProcessor
): TossPaymentsProcessor {

    override fun keyInPay(
        amount: Int,
        orderId: String,
        orderName: String,
        cardNumber: String,
        cardExpirationYear: String,
        cardExpirationMonth: String,
        cardPassword: String,
        customerIdentityNumber: String
    ) {
        val result = tossPaymentsKeyInApprovalProcessor.approval(
            TossPaymentsKeyInDto(
                amount,
                orderId,
                orderName,
                cardNumber,
                cardExpirationYear,
                cardExpirationMonth,
                cardPassword,
                customerIdentityNumber
            )
        )
    }
}