package org.collaborators.paymentslab.payment.domain

interface TossPaymentsRepository {
    fun save(entity: TossPayments): TossPayments
}