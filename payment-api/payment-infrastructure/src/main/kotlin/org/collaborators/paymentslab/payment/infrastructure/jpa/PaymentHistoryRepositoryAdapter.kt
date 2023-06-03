package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.PaymentHistory
import org.collaborators.paymentslab.payment.domain.PaymentHistoryRepository

class PaymentHistoryRepositoryAdapter(
    private val jpaPaymentHistoryRepository: JpaPaymentHistoryRepository): PaymentHistoryRepository {

    override fun save(entity: PaymentHistory): PaymentHistory {
        return jpaPaymentHistoryRepository.save(entity)
    }
}