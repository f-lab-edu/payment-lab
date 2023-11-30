package org.collaborators.paymentslab.payment.infrastructure

import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborator.paymentlab.common.inmemory.core.InMemoryRepository
import org.springframework.stereotype.Component

@Component
class InMemoryPaymentOrderRepository: InMemoryRepository<PaymentOrder, Long>(Long::class.java) {
}