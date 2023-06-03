package org.collaborators.paymentslab.payment.domain

interface PaymentHistoryRepository {
    fun save(entity: PaymentHistory): PaymentHistory
}