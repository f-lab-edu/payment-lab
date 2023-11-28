package org.collaborators.paymentslab.payment.infrastructure.inmemory

import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile(value = ["test"])
class InMemoryPaymentOrderRepositoryAdapter(
    private val inMemoryPaymentOrderRepository: InMemoryPaymentOrderRepository
): PaymentOrderRepository {
    override fun save(entity: PaymentOrder): PaymentOrder {
        return inMemoryPaymentOrderRepository.save(entity)
    }

    override fun findById(id: Long): PaymentOrder {
        return inMemoryPaymentOrderRepository.findById(id)!!
    }
}