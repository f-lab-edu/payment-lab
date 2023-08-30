package org.collaborators.paymentslab.log.domain

interface TransactionLogProcessor<T> {
    fun process(event: T)
}