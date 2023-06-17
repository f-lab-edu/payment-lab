package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.data.PageData
import org.collaborators.paymentslab.payment.domain.PaymentHistory
import org.collaborators.paymentslab.payment.domain.PaymentHistoryRepository
import org.collaborators.paymentslab.payment.domain.PaymentsQueryManager
import org.springframework.security.core.context.SecurityContextHolder

class TossPaymentsQueryManager(
    private val paymentsHistoryRepository: PaymentHistoryRepository
): PaymentsQueryManager {
    override fun readHistoriesFrom(
        pageNum: Int,
        pageSize: Int,
        direction: String,
        properties: List<String>
    ): List<PaymentHistory> {
        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        val accountId = principal.id
        return paymentsHistoryRepository.findAllByAccountId(accountId, PageData(pageNum, pageSize, direction, properties))
    }
}