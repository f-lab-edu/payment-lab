package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

open class TossPaymentsProcessor(
    private val tossPaymentsValidator: TossPaymentsValidator,
    private val tossPaymentsKeyInApprovalProcessor: TossPaymentsKeyInApprovalProcessor,
    private val paymentOrderRepository: PaymentOrderRepository,
): PaymentsProcessor {

    override fun process(
        paymentOrderId: Long,
        amount: Int,
        orderId: String,
        orderName: String,
        cardNumber: String,
        cardExpirationYear: String,
        cardExpirationMonth: String,
        cardPassword: String,
        customerIdentityNumber: String
    ) {
        val paymentOrder = paymentOrderRepository.findById(paymentOrderId)

        tossPaymentsValidator.validate(paymentOrder, amount, orderName)

//        tossPaymentsKeyInApprovalProcessor.approval(
//            paymentOrder,
//            TossPaymentsKeyInDto(
//                amount,
//                orderId,
//                orderName,
//                cardNumber,
//                cardExpirationYear,
//                cardExpirationMonth,
//                cardPassword,
//                customerIdentityNumber
//            )
//        )
    }
}