package org.collaborators.paymentslab.payment.application

import org.collaborators.paymentslab.payment.application.command.PaymentOrderCommand
import org.collaborators.paymentslab.payment.application.command.TossPaymentsKeyInPayCommand
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQuery
import org.collaborators.paymentslab.payment.application.query.PaymentHistoryQueryQueryModel
import org.collaborators.paymentslab.payment.domain.PaymentOrderProcessor
import org.collaborators.paymentslab.payment.domain.PaymentsProcessor
import org.collaborators.paymentslab.payment.domain.PaymentsQueryManager
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Profile(value = ["!test"])
@Transactional
class PaymentService(
    private val paymentsProcessor: PaymentsProcessor,
    private val paymentOrderProcessor: PaymentOrderProcessor,
    private val paymentsQueryManager: PaymentsQueryManager
    ) {

    @Transactional(propagation = Propagation.NEVER)
    fun keyInPay(paymentOrderId: Long, command: TossPaymentsKeyInPayCommand) {
        paymentsProcessor.process(
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
        val entities = paymentsQueryManager.queryHistory(
            query.pageNum, query.pageSize, query.direction, query.properties)
        return entities.map { PaymentHistoryQueryQueryModel.of(it) }
    }

    fun generatePaymentOrder(command: PaymentOrderCommand): String {
        // TODO 프론트에 리액트를 적용하기 전까지는 임시로 accountId 검증은 비활성화
        return paymentOrderProcessor.process(command.orderName, command.amount)
    }
}