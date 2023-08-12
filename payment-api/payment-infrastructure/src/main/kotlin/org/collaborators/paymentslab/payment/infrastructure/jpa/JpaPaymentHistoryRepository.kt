package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentHistoryRepository: JpaRepository<PaymentHistory, Long> {
    fun findPaymentHistoryById(id: Long): PaymentHistory
    fun findPaymentHistoriesByAccountId(accountId: Long, pageable: Pageable): Page<PaymentHistory>
}