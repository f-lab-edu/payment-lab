package org.collaborators.paymentslab.payment.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.DomainEventTypeMap
import org.collaborator.paymentlab.common.domain.DomainEvent
import org.collaborator.paymentlab.common.error.InvalidArgumentException
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
            val domainEvent = parseDomainEventFrom(record)
            val newPaymentHistoryEntity = PaymentHistory.newInstanceFrom(domainEvent)
            paymentHistoryRepository.save(newPaymentHistoryEntity)
        } catch (e: Exception) {
            logger.error("error occurred while record payment history: {}", e.message)
            throw InvalidArgumentException()
        }
    }

    private fun parseDomainEventFrom(record: String): DomainEvent {
        val domainEventMap = objectMapper.readValue(record, Map::class.java) as Map<String, String>
        val targetType = DomainEventTypeMap.typeFrom(domainEventMap)
        return objectMapper.convertValue(domainEventMap, targetType)
    }
}