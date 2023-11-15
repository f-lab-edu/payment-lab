package org.collaborators.paymentslab.payment.infrastructure.kafka

interface StringKafkaTemplateWrapper {
    fun send(topic: String, data: String)
}