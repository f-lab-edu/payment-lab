package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets
import java.util.*

class TossPaymentsKeyInApprovalProcessor(
    private val restTemplate: RestTemplate
) {
    @Value("\${toss.payments.url}")
    private lateinit var url: String

    @Value("\${toss.payments.secretKey}")
    private lateinit var secretKey: String

    fun approval(paymentOrder: PaymentOrder, dto: TossPaymentsKeyInDto): TossPaymentsApprovalResponse {
        try {
            val request = createRequest(paymentOrder, dto)
            val result = restTemplate.postForEntity("${url}key-in", request, TossPaymentsApprovalResponse::class.java)
            return result.body!!
        } catch (e: HttpClientErrorException) {
            throw TossPaymentsApiClientException(e)
        } catch (e: RestClientException) {
            throw ServiceException(ErrorCode.UN_DEFINED_ERROR)
        }
    }

    private fun createRequest(paymentOrder: PaymentOrder, dto: TossPaymentsKeyInDto): HttpEntity<TossPaymentsKeyInDto> {
        val headers = HttpHeaders()

        headers.setBasicAuth(String(Base64.getEncoder().encode("${secretKey}:".toByteArray(StandardCharsets.ISO_8859_1))))
        headers.contentType = MediaType.APPLICATION_JSON

        val account = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        val idempotencyKey = "po_${paymentOrder.id}_acc_${account.id}_sts_${paymentOrder.status}"
        headers.set("Idempotency-Key", idempotencyKey)

        return HttpEntity(dto, headers)
    }
}