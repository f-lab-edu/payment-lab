package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
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

    fun approval(dto: TossPaymentsKeyInDto): TossPaymentsApprovalResponse {
        try {
            val request = createRequest(dto)
            val result = restTemplate.postForEntity("${url}key-in", request, TossPaymentsApprovalResponse::class.java)
            return result.body!!
        } catch (e: HttpClientErrorException) {
            throw TossPaymentsApiClientException(e)
        } catch (e: RestClientException) {
            throw ServiceException(ErrorCode.UN_DEFINED_ERROR)
        }
    }

    private fun createRequest(dto: TossPaymentsKeyInDto): HttpEntity<TossPaymentsKeyInDto> {
        val headers = HttpHeaders()

        headers.setBasicAuth(String(Base64.getEncoder().encode("${secretKey}:".toByteArray(StandardCharsets.ISO_8859_1))))
        headers.contentType = MediaType.APPLICATION_JSON

        return HttpEntity(dto, headers)
    }
}