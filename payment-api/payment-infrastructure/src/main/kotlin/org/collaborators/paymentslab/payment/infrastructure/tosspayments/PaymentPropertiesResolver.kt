package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.springframework.beans.factory.annotation.Value

class PaymentPropertiesResolver(
    @Value("\${toss.payments.url}")
    val url: String,
    @Value("\${toss.payments.secretKey}")
    val secretKey: String,
    @Value("\${collaborators.kafka.topic.payment.transaction.name}")
    val paymentTransactionTopicName: String
)