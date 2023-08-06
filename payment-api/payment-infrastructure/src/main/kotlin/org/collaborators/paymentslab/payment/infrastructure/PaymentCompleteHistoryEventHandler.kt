package org.collaborators.paymentslab.payment.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.error.InvalidArgumentException
import org.collaborators.paymentslab.payment.domain.PaymentCompletedEvent
import org.collaborators.paymentslab.payment.domain.PaymentHistory
import org.collaborators.paymentslab.payment.domain.PaymentHistoryRepository
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PaymentCompleteHistoryEventHandler(
    private val paymentHistoryRepository: PaymentHistoryRepository
) {

    private val logger = LoggerFactory.getLogger("payment")

    @EventListener
    fun handle(event: PaymentCompletedEvent) {
        val objectMapper = ObjectMapper()
        val newPaymentHistoryEntity = PaymentHistory.newInstanceFrom(event)
        try {
            paymentHistoryRepository.save(newPaymentHistoryEntity)
            logger.info("history {}", objectMapper.writeValueAsString(newPaymentHistoryEntity))
        } catch (e: Exception) {
            logger.error(
                "error occurred while record payment history:  orderId: {}, paymentKey: {}",
                newPaymentHistoryEntity.orderId, newPaymentHistoryEntity.paymentKey
            )
            throw InvalidArgumentException()
        }

    }
}