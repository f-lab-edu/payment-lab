package org.collaborators.paymentslab.payment.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.domain.DomainEvent
import org.collaborator.paymentlab.common.error.InvalidArgumentException
import org.collaborators.paymentslab.payment.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.payment.domain.PaymentResultEvent
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.collaborators.paymentslab.payment.domain.repository.PaymentHistoryRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentHistoryEventHandler(
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = ["\${collaborators.kafka.topic.payment.transaction.name}"],
        groupId = "\${collaborators.kafka.topic.payment.transaction.groupId}")
    fun handle(record: String) {
        try {
            val idx = record.lastIndexOf(",")
            val eventJsonValue = record.substring(0, idx)
            val eventType = record.substring(idx + 1)
            val event = parseDomainEventFrom(eventJsonValue, eventType)
            val newPaymentHistoryEntity = if (eventType == PaymentResultEvent::class.simpleName) {
                PaymentHistory.newInstanceFrom(event as PaymentResultEvent)
            } else {
                PaymentHistory.newInstanceFrom(event as PaymentOrderRecordEvent)
            }
            paymentHistoryRepository.save(newPaymentHistoryEntity)
        } catch (e: Exception) {
            logger.error("error occurred while record payment history")
            throw InvalidArgumentException()
        }
    }

    private fun parseDomainEventFrom(eventJsonValue: String, eventType: String): DomainEvent {
        return if (eventType == PaymentResultEvent::class.simpleName)
                objectMapper.readValue(eventJsonValue, PaymentResultEvent::class.java)
            else
                objectMapper.readValue(eventJsonValue, PaymentOrderRecordEvent::class.java)
    }
}