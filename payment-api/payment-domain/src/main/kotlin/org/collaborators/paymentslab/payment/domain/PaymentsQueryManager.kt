package org.collaborators.paymentslab.payment.domain

interface PaymentsQueryManager {
    fun readHistoriesFrom(
        pageNum: Int,
        pageSize: Int,
        direction: String,
        properties: List<String>
    ): List<PaymentHistory>
}