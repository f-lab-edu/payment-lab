package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.domain.TossPaymentsProcessor
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.hibernate.type.descriptor.jdbc.JdbcType.isNumber
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException

@Service
@Profile(value = ["test"])
@Transactional
class MockPaymentService(private val tossPaymentsProcessor: TossPaymentsProcessor): PaymentService(tossPaymentsProcessor) {
    override fun keyInPay(command: TossPaymentsKeyInPayCommand) {
        val cardNumber = command.cardNumber
        if (cardNumber.length != 16 || isDigits(cardNumber))
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