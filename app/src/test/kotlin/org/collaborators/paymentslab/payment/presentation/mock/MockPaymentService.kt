package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQuery
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQueryQueryModel
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.domain.PaymentsQueryManager
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.TossPaymentsValidator
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.TossPaymentsApiClientException
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.util.*

@Service
@Profile(value = ["test"])
@Transactional
class MockPaymentService(
    private val paymentsProcessor: PaymentsProcessor,
    private val paymentsQueryManager: PaymentsQueryManager,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val tossPaymentsValidator: TossPaymentsValidator
): PaymentService(paymentsProcessor, paymentsQueryManager) {
    override fun keyInPay(paymentOrderId: Long, command: TossPaymentsKeyInPayCommand) {
        val paymentOrder = paymentOrderRepository.findById(paymentOrderId)
        tossPaymentsValidator.validate(paymentOrder, command.amount, command.orderName)

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