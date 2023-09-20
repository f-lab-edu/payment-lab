package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.PaymentOrderProcessor
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.InvalidPaymentOrderException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.core.context.SecurityContextHolder

class TossPaymentsPaymentOrderProcessor(
    private val paymentOrderRepository: PaymentOrderRepository
): PaymentOrderProcessor {

    override fun process(accountId: Long, orderName: String, amount: Int): String {
        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        if (accountId != principal.id)
            throw InvalidPaymentOrderException()
        val newPaymentOrder = PaymentOrder.newInstance(accountId, orderName, amount)
        paymentOrderRepository.save(newPaymentOrder)

        return newPaymentOrder.id.toString()
    }
}