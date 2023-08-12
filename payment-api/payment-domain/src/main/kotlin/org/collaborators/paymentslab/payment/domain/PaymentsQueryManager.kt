package org.collaborators.paymentslab.payment.domain

import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory

interface PaymentsQueryManager {
    fun readHistoriesFrom(
        pageNum: Int,
        pageSize: Int,
        direction: String,
        properties: List<String>
    ): List<PaymentHistory>
}