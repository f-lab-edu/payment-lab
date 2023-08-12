package org.collaborators.paymentslab.payment.infrastructure.jpa

import org.collaborators.paymentslab.payment.data.PageData
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.collaborators.paymentslab.payment.domain.repository.PaymentHistoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class TossPaymentHistoryRepositoryAdapter(
    private val jpaPaymentHistoryRepository: JpaPaymentHistoryRepository): PaymentHistoryRepository {

    override fun save(entity: PaymentHistory): PaymentHistory {
        return jpaPaymentHistoryRepository.save(entity)
    }

    override fun findById(id: Long): PaymentHistory {
        return jpaPaymentHistoryRepository.findPaymentHistoryById(id)
    }

    override fun findAll(pageData: PageData): List<PaymentHistory> {
        val direction = Sort.Direction.valueOf(pageData.direction)
        val properties = pageData.properties.toTypedArray()
        val pageable = PageRequest.of(pageData.pageNum, pageData.pageSize, direction, *properties)
        return jpaPaymentHistoryRepository.findAll(pageable)
            .content
    }

    override fun findAllByAccountId(accountId: Long, pageData: PageData): List<PaymentHistory> {
        val direction = Sort.Direction.valueOf(pageData.direction)
        val properties = pageData.properties.toTypedArray()
        val pageable = PageRequest.of(pageData.pageNum, pageData.pageSize, direction, *properties)
        return jpaPaymentHistoryRepository.findPaymentHistoriesByAccountId(accountId, pageable)
            .content
    }
}