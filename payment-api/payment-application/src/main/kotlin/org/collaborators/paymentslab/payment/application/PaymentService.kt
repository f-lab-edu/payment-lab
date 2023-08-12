package org.collaborators.paymentslab.payment.application

import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQuery
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQueryQueryModel
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.domain.PaymentsQueryManager
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Profile(value = ["!test"])
@Transactional
class PaymentService(
    private val paymentsProcessor: PaymentsProcessor,
    private val paymentsQueryManager: PaymentsQueryManager
    ) {
    fun keyInPay(paymentOrderId: Long, command: TossPaymentsKeyInPayCommand) {
        paymentsProcessor.keyInPay(
            paymentOrderId,
            command.amount,
            command.orderId,
            command.orderName,
            command.cardNumber,
            command.cardExpirationYear,
            command.cardExpirationMonth,
            command.cardPassword,
            command.customerIdentityNumber
        )
    }

    fun readHistoriesFrom(query: PaymentHistoryQuery): List<PaymentHistoryQueryQueryModel> {
        val entities = paymentsQueryManager.readHistoriesFrom(
            query.pageNum, query.pageSize, query.direction, query.properties)
        return entities.map { PaymentHistoryQueryQueryModel.of(it) }
    }
}