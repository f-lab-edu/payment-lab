package org.collaborators.paymentslab.payment.infrastructure

import org.collaborator.paymentlab.common.domain.RestClient
import org.collaborators.paymentslab.payment.domain.entity.PaymentsStatus
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.TossPaymentsApprovalResponse
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.TossPaymentsCardInfoResponse
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.TossPaymentsKeyInDto
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.collaborators.paymentslab.payment.presentation.mock.MockTossPaymentsErrorBody
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@Profile(value = ["test"])
class TestTossPaymentsRestClient: RestClient<TossPaymentsKeyInDto, TossPaymentsApprovalResponse> {
    override fun keyIn(
        url: String,
        request: HttpEntity<TossPaymentsKeyInDto>
    ): ResponseEntity<TossPaymentsApprovalResponse> {
        val reqBody = request.body ?: throw NullPointerException()

        if (reqBody.cardNumber.length != 16 || !isDigits(reqBody.cardNumber))
            throw TossPaymentsApiClientException(MockTossPaymentsErrorBody.invalidCardNumber)

        return ResponseEntity.ok(TossPaymentsApprovalResponse(
            mId = "tvivarepublica4",
            lastTransactionKey = "2A441542485089863EB31F9B039FEFF8",
            paymentKey = "4qjZblEopLBa5PzR0Arn9KeQDGJPxkVvmYnNeDMyW2G1OgwK",
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

    private fun isDigits(cardNum: String): Boolean {
        val arr = cardNum.toCharArray()
        for (i in arr.indices) {
            if (!Character.isDigit(arr[i]))
                return false
        }
        return true
    }
}