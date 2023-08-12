package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentOrderRepository: JpaRepository<PaymentOrder, Long> {
    fun findPaymentOrderById(id: Long): PaymentOrder
}