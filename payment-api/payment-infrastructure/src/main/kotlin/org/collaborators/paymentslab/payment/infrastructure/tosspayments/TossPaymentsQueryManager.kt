package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.data.PageData
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.collaborators.paymentslab.payment.domain.repository.PaymentHistoryRepository
import org.collaborators.paymentslab.payment.domain.PaymentsQueryManager
import org.collaborators.paymentslab.payment.infrastructure.getCurrentAccount

class TossPaymentsQueryManager(
    private val paymentsHistoryRepository: PaymentHistoryRepository
): PaymentsQueryManager {
    override fun queryHistory(
        pageNum: Int,
        pageSize: Int,
        direction: String,
        properties: List<String>
    ): List<PaymentHistory> {
        val currentAccount = getCurrentAccount()
        val accountId = currentAccount.id
        return paymentsHistoryRepository.findAllByAccountId(accountId, PageData(pageNum, pageSize, direction, properties))
    }
}