package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.entity.TossPayments
import org.springframework.data.jpa.repository.JpaRepository

interface JpaTossPaymentRepository: JpaRepository<TossPayments, Long> {
}