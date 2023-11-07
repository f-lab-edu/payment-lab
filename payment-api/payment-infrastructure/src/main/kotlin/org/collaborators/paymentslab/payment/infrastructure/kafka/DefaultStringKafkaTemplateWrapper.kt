package org.collaborators.paymentslab.payment.infrastructure.kafka

import org.springframework.kafka.core.KafkaTemplate

class DefaultStringKafkaTemplateWrapper(
    private val kafkaTemplate: KafkaTemplate<String, String>
): StringKafkaTemplateWrapper {
    override fun send(topic: String, data: String) {
        kafkaTemplate.send(topic, data)
    }
}