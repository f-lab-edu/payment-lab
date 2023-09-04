package org.collaborators.paymentslab.payment.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.error.InvalidArgumentException
import org.collaborators.paymentslab.payment.domain.PaymentResultEvent
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.collaborators.paymentslab.payment.domain.repository.PaymentHistoryRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentCompleteHistoryEventHandler(
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = ["\${collaborators.kafka.topic.payment.transaction.name}"],
        groupId = "\${collaborators.kafka.topic.payment.transaction.groupId}")
    fun handle(record: String) {
        val event = objectMapper.readValue(record, PaymentResultEvent::class.java)
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