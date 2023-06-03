package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentHistoryRepository: JpaRepository<PaymentHistory, Long> {
}