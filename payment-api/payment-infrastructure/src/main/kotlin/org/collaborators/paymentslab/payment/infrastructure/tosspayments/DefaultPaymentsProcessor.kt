package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.PaymentHistory
import org.collaborators.paymentslab.payment.domain.PaymentHistoryRepository
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.domain.TossPaymentsRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.core.context.SecurityContextHolder

class DefaultPaymentsProcessor(
    private val tossPaymentsKeyInApprovalProcessor: TossPaymentsKeyInApprovalProcessor,
    private val tossPaymentsRepository: TossPaymentsRepository,

    // TODO 결제이력 eventListener 구현 이후 삭제
    private val paymentHistoryRepository: PaymentHistoryRepository,

    private val publisher: ApplicationEventPublisher
): PaymentsProcessor {

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

        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser

        val newPaymentEntity = TossPaymentsFactory.create(response)
        newPaymentEntity.completeOf(principal.id)
        val newPaymentRecord = tossPaymentsRepository.save(newPaymentEntity)

        val newPaymentHistoryEntity = PaymentHistory.newInstanceFrom(newPaymentRecord)
        paymentHistoryRepository.save(newPaymentHistoryEntity)
        // TODO EventListener 를 활용하여, 비동기로 결제이력 저장하는 코드 작성
//        newPaymentRecord.pollAllEvents().forEach { publisher.publishEvent(it) }
    }
}