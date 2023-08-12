package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.domain.repository.TossPaymentsRepository
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional

open class TossPaymentsProcessor(
    private val tossPaymentsValidator: TossPaymentsValidator,
    private val tossPaymentsKeyInApprovalProcessor: TossPaymentsKeyInApprovalProcessor,
    private val tossPaymentsRepository: TossPaymentsRepository,
    private val publisher: ApplicationEventPublisher,
    private val objectMapper: ObjectMapper
): PaymentsProcessor {

    private val logger = LoggerFactory.getLogger("payment")

    @Transactional
    override fun keyInPay(
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
        tossPaymentsValidator.validate(paymentOrderId, amount, orderName)

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