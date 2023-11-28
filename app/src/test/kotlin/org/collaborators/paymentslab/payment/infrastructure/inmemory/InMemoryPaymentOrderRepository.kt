package org.collaborators.paymentslab.payment.infrastructure.inmemory

import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.infrastructure.inmemory.core.IdGenerator
import org.collaborators.paymentslab.payment.infrastructure.inmemory.core.IdGeneratorFactory
import org.collaborators.paymentslab.payment.infrastructure.inmemory.core.InMemoryRepository
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryPaymentOrderRepository: InMemoryRepository<PaymentOrder, Long>(Long::class.java) {
}