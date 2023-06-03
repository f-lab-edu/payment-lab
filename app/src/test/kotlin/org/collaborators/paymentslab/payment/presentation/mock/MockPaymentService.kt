package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Profile(value = ["test"])
@Transactional
class MockPaymentService(private val paymentsProcessor: PaymentsProcessor): PaymentService(paymentsProcessor) {
    override fun keyInPay(command: TossPaymentsKeyInPayCommand) {
        val cardNumber = command.cardNumber
        if (cardNumber.length != 16 || !isDigits(cardNumber))
            throw TossPaymentsApiClientException(MockTossPaymentsErrorBody.invalidCardNumber)
    }

    private fun isDigits(cardNum: String): Boolean {
        val arr = cardNum.toCharArray()
        for (i in arr.indices) {
            if (!Character.isDigit(arr[i]))
                return false
        }
        return true;
    }
}