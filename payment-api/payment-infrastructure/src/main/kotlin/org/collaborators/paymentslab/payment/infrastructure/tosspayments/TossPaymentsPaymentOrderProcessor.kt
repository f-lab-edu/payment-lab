package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.PaymentOrderProcessor
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.InvalidPaymentOrderException
import org.springframework.security.core.context.SecurityContextHolder

class TossPaymentsPaymentOrderProcessor(
    private val paymentOrderRepository: PaymentOrderRepository
): PaymentOrderProcessor {
    override fun process(accountId: Long, orderName: String, amount: Int): String {
        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        // TODO 프론트에 jwt 토큰 값을 파싱하는 기능을 추가하기 전까지만 비활성화
//        if (accountId != principal.id)
//            throw InvalidPaymentOrderException()
        val newPaymentOrder = PaymentOrder.newInstance(principal.id, orderName, amount)
        paymentOrderRepository.save(newPaymentOrder)
        return newPaymentOrder.id.toString()
    }
}