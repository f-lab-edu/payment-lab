package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.account.domain.Account
import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.entity.PaymentsStatus
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.TossPaymentsApprovalResponse
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.TossPaymentsCardInfoResponse
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.TossPaymentsKeyInDto
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

    fun mockPaymentHistory(account: Account, paymentOrderId: Long = 1L): PaymentHistory {
        val paymentHistory = PaymentHistory.newInstanceFrom(
            account.id()!!,
            paymentOrderId,
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
    fun mockTossPaymentsKeyInDto(command: TossPaymentsKeyInPayCommand) = TossPaymentsKeyInDto(
        command.amount,
        command.orderId,
        command.orderName,
        command.cardNumber,
        command.cardExpirationYear,
        command.cardExpirationMonth,
        command.cardPassword,
        command.customerIdentityNumber
    )

    fun mockTossPaymentsApproval(reqBody: TossPaymentsKeyInDto) = TossPaymentsApprovalResponse(
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
    )
}