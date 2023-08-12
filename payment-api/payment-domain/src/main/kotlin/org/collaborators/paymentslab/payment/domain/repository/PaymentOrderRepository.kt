package org.collaborators.paymentslab.payment.domain.repository

import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder

interface PaymentOrderRepository {
    fun save(entity: PaymentOrder): PaymentOrder
    fun findById(id: Long): PaymentOrder?
}