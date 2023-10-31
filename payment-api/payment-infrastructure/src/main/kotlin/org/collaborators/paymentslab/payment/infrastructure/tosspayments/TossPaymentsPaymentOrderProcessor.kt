package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.PaymentOrderProcessor
import org.collaborators.paymentslab.payment.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.infrastructure.log.AsyncAppenderPaymentTransactionLogProcessor
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.InvalidPaymentOrderException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.core.context.SecurityContextHolder

class TossPaymentsPaymentOrderProcessor(
    private val paymentOrderRepository: PaymentOrderRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val environment: Environment,
    private val asyncLogProcessor: AsyncAppenderPaymentTransactionLogProcessor
): PaymentOrderProcessor {

    @Value("\${collaborators.kafka.topic.payment.transaction.name}")
    private lateinit var paymentTransactionTopicName: String

    override fun process(accountId: Long, orderName: String, amount: Int) {
        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        val newPaymentOrder = PaymentOrder.newInstance(accountId, orderName, amount)
        val paymentOrder = paymentOrderRepository.save(newPaymentOrder)
        if (accountId != principal.id) {
            paymentOrder.aborted()
            throw InvalidPaymentOrderException()
        }
        paymentOrder.ready()
        paymentOrder.pollAllEvents().forEach {
            asyncLogProcessor.process(it as PaymentOrderRecordEvent)
            if (!environment.activeProfiles.contains("test")) {
                kafkaTemplate.send(paymentTransactionTopicName, objectMapper.writeValueAsString(it))
            }
        }
    }
}