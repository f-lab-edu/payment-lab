package org.collaborators.paymentslab.payment.infrastructure.tosspayments


import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.infrastructure.getCurrentAccount
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import java.nio.charset.StandardCharsets
import java.util.*

class TossPaymentsKeyInApprovalProcessor(
    private val tossPaymentsRestClient: TossPaymentsRestClient,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentsTransactionEventPublisher: TossPaymentsTransactionEventPublisher,
    private val paymentProperties: PaymentPropertiesResolver
) {
    fun approval(paymentOrder: PaymentOrder, dto: TossPaymentsKeyInDto) {
        paymentOrder.inProcess()
        var result = TossPaymentsApprovalResponse.preResponseOf(paymentOrder, dto)
        try {
            val request = createRequest(paymentOrder, dto)
            val response = tossPaymentsRestClient.keyIn("${paymentProperties.url}key-in", request)
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
            paymentsTransactionEventPublisher.publishAndRecord(result, paymentOrder)
        }
    }

    private fun createRequest(paymentOrder: PaymentOrder, dto: TossPaymentsKeyInDto): HttpEntity<TossPaymentsKeyInDto> {
        val headers = HttpHeaders()

        headers.setBasicAuth(String(Base64.getEncoder().encode("${paymentProperties.secretKey}:".toByteArray(StandardCharsets.ISO_8859_1))))
        headers.contentType = MediaType.APPLICATION_JSON

        val account = getCurrentAccount()
        val idempotencyKey = "po_${paymentOrder.id()}_acc_${account.id}}"
        headers.set("Idempotency-Key", String(Base64.getEncoder().encode(idempotencyKey.toByteArray(StandardCharsets.ISO_8859_1))))

        return HttpEntity(dto, headers)
    }
}