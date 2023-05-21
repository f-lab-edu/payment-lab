package org.collaborator.paymentlab.common.domain

import java.util.Date

interface DomainEvent {
    fun occurredOn(): Date
}