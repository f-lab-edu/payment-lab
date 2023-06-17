package org.collaborators.paymentslab.payment.domain

import org.collaborators.paymentslab.payment.data.PageData

interface PaymentHistoryRepository {
    fun save(entity: PaymentHistory): PaymentHistory
    fun findById(id: Long): PaymentHistory
    fun findAll(pageData: PageData): List<PaymentHistory>
    fun findAllByAccountId(accountId: Long, pageData: PageData): List<PaymentHistory>
}