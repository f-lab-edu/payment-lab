package org.collaborators.paymentslab.payment.infrastructure.inmemory.core

interface IdGenerator<T> {
    fun generateId(): T
}