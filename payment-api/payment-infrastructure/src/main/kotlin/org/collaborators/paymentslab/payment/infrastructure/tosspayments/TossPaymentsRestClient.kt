package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.domain.RestClient
import org.collaborators.paymentslab.payment.domain.entity.PaymentsStatus
import org.collaborator.paymentlab.common.PaymentFeature
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Profile(value = ["!test"])
class TossPaymentsRestClient(
    private val restTemplate: RestTemplate
): RestClient<TossPaymentsKeyInDto, TossPaymentsApprovalResponse> {
    override fun keyIn(
        url: String,
        request: HttpEntity<TossPaymentsKeyInDto>
    ): ResponseEntity<TossPaymentsApprovalResponse> {
        if (PaymentFeature.TOSS_PAYMENTS_FEATURE.isActive())
            return restTemplate.postForEntity(url, request, TossPaymentsApprovalResponse::class.java)

        // 테스트용 결제승인 요청을 위해 RestClient 를 컴포지션 패턴으로 재정의
        val client = object : RestClient<TossPaymentsKeyInDto, TossPaymentsApprovalResponse> {
            override fun keyIn(url: String, request: HttpEntity<TossPaymentsKeyInDto>): ResponseEntity<TossPaymentsApprovalResponse> {
                val reqBody = request.body ?: throw NullPointerException()
                val objectMapper = ObjectMapper()
                Thread.sleep((300L..1000L).random())
                return ResponseEntity.ok(TossPaymentsApprovalResponse(
                    mId = "tvivarepublica4",
                    lastTransactionKey = "2A441542485089863EB31F9B039FEFF8",
                    paymentKey = objectMapper.writeValueAsString(reqBody),
                    orderId = reqBody.orderId,
                    orderName = reqBody.orderName,
                    taxExemptionAmount = 0,
                    status = PaymentsStatus.DONE.name,
                    useEscrow = false,
                    cultureExpense = false,
                    card = TossPaymentsCardInfoResponse(
                        issuerCode = "4V",
                        acquirerCode = "21",
                        number = reqBody.cardNumber,
                        installmentPlanMonths = 0,
                        isInterestFree = false,
                        approveNo = "00000000",
                        useCardPoint = false,
                        cardType = "미확인",
                        ownerType =  "미확인",
                        acquireStatus = "READY",
                        amount = reqBody.amount
                    )
                ))
            }
        }
        return client.keyIn(url, request)
    }
}