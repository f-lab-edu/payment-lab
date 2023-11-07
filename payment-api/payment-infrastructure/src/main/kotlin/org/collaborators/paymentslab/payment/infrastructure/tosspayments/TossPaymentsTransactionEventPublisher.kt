package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborator.paymentlab.common.domain.DomainEventTypeParser
import org.collaborators.paymentslab.payment.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.payment.domain.PaymentResultEvent
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.domain.repository.TossPaymentsRepository
import org.collaborators.paymentslab.payment.infrastructure.log.AsyncAppenderPaymentTransactionLogProcessor
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.InvalidPaymentOrderException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.core.context.SecurityContextHolder

class TossPaymentsTransactionEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val asyncLogProcessor: AsyncAppenderPaymentTransactionLogProcessor,
    private val tossPaymentsRepository: TossPaymentsRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val objectMapper: ObjectMapper,
    private val paymentProperties: PaymentPropertiesResolver,
) {

    fun publishAndRecord(result: TossPaymentsApprovalResponse, paymentOrder: PaymentOrder) {
        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        val newPaymentEntity = TossPaymentsFactory.create(result)
        newPaymentEntity.resultOf(principal.id, paymentOrder.status)
        val newPaymentRecord = tossPaymentsRepository.save(newPaymentEntity)

        newPaymentRecord.pollAllEvents().forEach {
            asyncLogProcessor.process(it as PaymentResultEvent)
            val eventWithClassType =
                DomainEventTypeParser.parseSimpleName(objectMapper.writeValueAsString(it), it::class.java)
            kafkaTemplate.send(paymentProperties.paymentTransactionTopicName, eventWithClassType)
        }
    }

    fun publishAndRecord(accountId: Long, orderName: String, amount: Int) {
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
            kafkaTemplate.send(paymentProperties.paymentTransactionTopicName, objectMapper.writeValueAsString(it))
        }
    }
}