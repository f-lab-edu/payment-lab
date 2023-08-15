package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.payment.presentation.request.TossPaymentsKeyInRequest

object MockPayments {
    val testTossPaymentsRequest = TossPaymentsKeyInRequest(
        amount = 10000,
        orderName = "테스트 주문 상품",
        cardNumber = "4333243211232998",
        cardExpirationYear = "24",
        cardExpirationMonth = "09",
        cardPassword = "12",
        customerIdentityNumber = "991212",
    )

    val invalidCardNumberTestTossPaymentsRequest = TossPaymentsKeyInRequest(
        amount = 10000,
        orderName = "테스트 주문 상품",
        cardNumber = "433....2342345",
        cardExpirationYear = "24",
        cardExpirationMonth = "09",
        cardPassword = "12",
        customerIdentityNumber = "991212",
    )
}