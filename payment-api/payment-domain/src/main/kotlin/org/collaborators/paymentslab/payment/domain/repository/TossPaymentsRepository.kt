package org.collaborators.paymentslab.payment.domain.repository

import org.collaborators.paymentslab.payment.domain.entity.TossPayments

interface TossPaymentsRepository {
    fun save(entity: TossPayments): TossPayments
}