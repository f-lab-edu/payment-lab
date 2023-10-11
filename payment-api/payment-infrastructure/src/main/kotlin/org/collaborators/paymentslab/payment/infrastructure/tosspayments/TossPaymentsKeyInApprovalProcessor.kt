package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborator.paymentlab.common.domain.DomainEventTypeParser
import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.domain.repository.TossPaymentsRepository
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets
import java.util.*

class TossPaymentsKeyInApprovalProcessor(
    private val restTemplate: RestTemplate,
    private val tossPaymentsRepository: TossPaymentsRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val publisher: ApplicationEventPublisher,
    private val environment: Environment
) {
    @Value("\${toss.payments.url}")
    private lateinit var url: String

    @Value("\${toss.payments.secretKey}")
    private lateinit var secretKey: String

    @Value("\${collaborators.kafka.topic.payment.transaction.name}")
    private lateinit var paymentTransactionTopicName: String

    fun approval(paymentOrder: PaymentOrder, dto: TossPaymentsKeyInDto) {
        paymentOrder.inProcess()
        var result = TossPaymentsApprovalResponse.preResponseOf(paymentOrder, dto)
        try {
            val request = createRequest(paymentOrder, dto)
            val response = restTemplate.postForEntity("${url}key-in", request, TossPaymentsApprovalResponse::class.java)
            if (response.statusCode == HttpStatus.OK && response.hasBody()) {
                paymentOrder.complete()
                result = response.body!!
            }
        } catch (e: HttpClientErrorException) {
            paymentOrder.aborted()
            throw TossPaymentsApiClientException(e)
        } catch (e: RestClientException) {
            paymentOrder.aborted()
            throw ServiceException(ErrorCode.UN_DEFINED_ERROR)
        } finally {
            paymentOrderRepository.save(paymentOrder)
            publishEventForRecordPaymentProcess(result, paymentOrder)
        }
    }

    private fun publishEventForRecordPaymentProcess(
        result: TossPaymentsApprovalResponse,
        paymentOrder: PaymentOrder
    ) {
        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        val newPaymentEntity = TossPaymentsFactory.create(result)
        newPaymentEntity.resultOf(principal.id, paymentOrder.status)
        val newPaymentRecord = tossPaymentsRepository.save(newPaymentEntity)

        newPaymentRecord.pollAllEvents().forEach {
            publisher.publishEvent(it)
            if (!environment.activeProfiles.contains("test")) {
                val eventWithClassType =
                    DomainEventTypeParser.parseSimpleName(objectMapper.writeValueAsString(it), it::class.java)
                kafkaTemplate.send(paymentTransactionTopicName, eventWithClassType)
            }
        }
    }

    private fun createRequest(paymentOrder: PaymentOrder, dto: TossPaymentsKeyInDto): HttpEntity<TossPaymentsKeyInDto> {
        val headers = HttpHeaders()

        headers.setBasicAuth(String(Base64.getEncoder().encode("${secretKey}:".toByteArray(StandardCharsets.ISO_8859_1))))
        headers.contentType = MediaType.APPLICATION_JSON

        val account = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        val idempotencyKey = "po_${paymentOrder.id}_acc_${account.id}}"
        headers.set("Idempotency-Key", String(Base64.getEncoder().encode(idempotencyKey.toByteArray(StandardCharsets.ISO_8859_1))))

        return HttpEntity(dto, headers)
    }
}