package org.collaborator.paymentlab.common.inmemory.core

class SequentialLongIdGenerator: IdGenerator<Long> {
    private var sequence = 1L

    override fun generateId(): Long {
        return this.sequence++
    }
}