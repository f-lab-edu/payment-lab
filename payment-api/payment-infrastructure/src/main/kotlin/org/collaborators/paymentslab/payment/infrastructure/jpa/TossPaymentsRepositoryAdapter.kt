package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.entity.TossPayments
import org.collaborators.paymentslab.payment.domain.repository.TossPaymentsRepository

class TossPaymentsRepositoryAdapter(private val jpaTossPaymentRepository: JpaTossPaymentRepository):
    TossPaymentsRepository {
    override fun save(entity: TossPayments): TossPayments {
        return jpaTossPaymentRepository.save(entity)
    }
}