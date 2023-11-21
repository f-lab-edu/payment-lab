package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.domain.RestClient
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class TossPaymentsRestClient(
    private val restTemplate: RestTemplate,
    private val paymentProperties: PaymentPropertiesResolver
): RestClient<TossPaymentsKeyInDto, TossPaymentsApprovalResponse> {
    override fun postForEntity(
        url: String,
        request: HttpEntity<TossPaymentsKeyInDto>
    ): ResponseEntity<TossPaymentsApprovalResponse> {
        return restTemplate.postForEntity("${paymentProperties.url}key-in", request, TossPaymentsApprovalResponse::class.java)
    }
}