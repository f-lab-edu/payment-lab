package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.infrastructure.togglz.PaymentFeature
import org.springframework.beans.factory.annotation.Value

class PaymentPropertiesResolver(
    @Value("\${toss.payments.url}")
    val keyInUrl: String,
    @Value("\${toss.payments.url}")
    val url: String,
    @Value("\${toss.payments.secretKey}")
    val secretKey: String,
    @Value("\${collaborators.kafka.topic.payment.transaction.name}")
    val paymentTransactionTopicName: String
) {
    fun url(): String {
        var ret = keyInUrl
        if (!PaymentFeature.TOSS_PAYMENTS_FEATURE.isActive()) ret = "disable!!!!!!"
        return ret
    }
}