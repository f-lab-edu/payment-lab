package org.collaborators.paymentslab.payment.infrastructure.inmemory.core

interface Identified<ID> {
    fun getId(): ID
}