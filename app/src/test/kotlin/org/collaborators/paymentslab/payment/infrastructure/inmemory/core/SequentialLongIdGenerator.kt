package org.collaborators.paymentslab.payment.infrastructure.inmemory.core

class SequentialLongIdGenerator: IdGenerator<Long> {
    private var sequence = 1L;

    override fun generateId(): Long {
        return this.sequence++
    }
}