package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.domain.TossPaymentsProcessor
import org.collaborators.paymentslab.payment.domain.TossPaymentsRepository
import org.springframework.context.ApplicationEventPublisher

class DefaultTossPaymentsProcessor(
    private val tossPaymentsKeyInApprovalProcessor: TossPaymentsKeyInApprovalProcessor,
    private val tossPaymentsRepository: TossPaymentsRepository,
    private val publisher: ApplicationEventPublisher
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
        val response = tossPaymentsKeyInApprovalProcessor.approval(
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

        //
        val tossPayments = TossPaymentsFactory.create(response)
        tossPaymentsRepository.save(tossPayments)
    }
}