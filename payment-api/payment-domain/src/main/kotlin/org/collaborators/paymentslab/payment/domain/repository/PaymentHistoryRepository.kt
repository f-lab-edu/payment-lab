package org.collaborators.paymentslab.payment.domain.repository

import org.collaborators.paymentslab.payment.data.PageData
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory

interface PaymentHistoryRepository {
    fun save(entity: PaymentHistory): PaymentHistory
    fun findById(id: Long): PaymentHistory
    fun findAll(pageData: PageData): List<PaymentHistory>
    fun findAllByAccountId(accountId: Long, pageData: PageData): List<PaymentHistory>
}