package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.PaymentHistoryRepository
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.domain.TossPaymentsRepository
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional

open class DefaultPaymentsProcessor(
    private val tossPaymentsKeyInApprovalProcessor: TossPaymentsKeyInApprovalProcessor,
    private val tossPaymentsRepository: TossPaymentsRepository,

    // TODO 결제이력 eventListener 구현 이후 삭제
    private val paymentHistoryRepository: PaymentHistoryRepository,

    private val publisher: ApplicationEventPublisher,
    private val objectMapper: ObjectMapper
): PaymentsProcessor {

    private val logger = LoggerFactory.getLogger("payment")

    @Transactional
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

        newPaymentRecord.pollAllEvents().forEach {
            logger.info("complete {}", objectMapper.writeValueAsString(it))
            publisher.publishEvent(it)
        }
    }
}