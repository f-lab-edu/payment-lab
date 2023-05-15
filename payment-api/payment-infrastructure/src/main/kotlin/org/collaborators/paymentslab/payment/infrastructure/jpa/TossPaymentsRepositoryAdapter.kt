package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.TossPayments
import org.collaborators.paymentslab.payment.domain.TossPaymentsRepository

class TossPaymentsRepositoryAdapter(private val jpaTossPaymentRepository: JpaTossPaymentRepository): TossPaymentsRepository {
    override fun save(entity: TossPayments): TossPayments {
        return jpaTossPaymentRepository.save(entity)
    }
}