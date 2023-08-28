package org.collaborators.paymentslab.log.domain

interface TransactionLogProcessor<T> {
    fun execute(event: T)
}