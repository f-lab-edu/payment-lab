package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.account.domain.Account
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.presentation.request.TossPaymentsKeyInRequest
import java.time.LocalDateTime

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

    fun mockPaymentHistory(account: Account): PaymentHistory {
        val paymentHistory = PaymentHistory.newInstanceFrom(
            account.id()!!,
            LocalDateTime.now(),
            "ord_202306172137299642490491",
            "테스트결제",
            10000,
            "testPaymentKey",
            "DONE"
        )
        paymentHistory.id = 1L
        return paymentHistory
    }

    fun mockMutatedReadyPaymentOrder(account: Account): PaymentOrder {
        val paymentOrder = PaymentOrder.newInstance(
            account.id ?: 1L,
            testTossPaymentsRequest.orderName,
            20000
        )
        paymentOrder.id = 1L
        paymentOrder.ready()
        return paymentOrder
    }

    fun mockCreatedPaymentOrder(account: Account): PaymentOrder {
        val paymentOrder = PaymentOrder.newInstance(
            account.id ?: 1L,
            testTossPaymentsRequest.orderName,
            testTossPaymentsRequest.amount
        )
        paymentOrder.id = 1L
        return paymentOrder
    }

    fun mockReadyPaymentOrder(account: Account): PaymentOrder {
        val paymentOrder = PaymentOrder.newInstance(
            account.id ?: 1L,
            testTossPaymentsRequest.orderName,
            testTossPaymentsRequest.amount
        )
        paymentOrder.id = 1L
        paymentOrder.ready()
        return paymentOrder
    }

    fun mockCanceledPaymentOrder(account: Account): PaymentOrder {
        val paymentOrder = PaymentOrder.newInstance(
            account.id ?: 1L,
            testTossPaymentsRequest.orderName,
            testTossPaymentsRequest.amount
        )
        paymentOrder.id = 1L
        paymentOrder.cancel()
        return paymentOrder
    }
}