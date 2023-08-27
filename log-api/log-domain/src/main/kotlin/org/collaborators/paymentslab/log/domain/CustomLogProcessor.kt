package org.collaborators.paymentslab.log.domain

interface CustomLogProcessor<T> {
    fun execute(event: T)
}