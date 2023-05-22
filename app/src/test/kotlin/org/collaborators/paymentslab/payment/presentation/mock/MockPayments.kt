package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.presentation.request.TossPaymentsKeyInRequest

object MockPayments {
    val testTossPaymentsRequest = TossPaymentsKeyInRequest(
        amount = 10000,
        orderName = "테스트 주문 상품",
        cardNumber = "4330123412342345",
        cardExpirationYear = "24",
        cardExpirationMonth = "09",
        cardPassword = "12",
        customerIdentityNumber = "991212",
    )
}