package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.PaymentOrderNotFoundException
import org.springframework.context.annotation.Profile


@Profile(value = ["!test"])
class PaymentOrderRepositoryAdapter(
    private val jpaPaymentOrderRepository: JpaPaymentOrderRepository
): PaymentOrderRepository {
    override fun save(entity: PaymentOrder): PaymentOrder {
        return jpaPaymentOrderRepository.save(entity)
    }

    override fun findById(id: Long): PaymentOrder {
        return jpaPaymentOrderRepository.findPaymentOrderById(id) ?: throw PaymentOrderNotFoundException()
    }
}