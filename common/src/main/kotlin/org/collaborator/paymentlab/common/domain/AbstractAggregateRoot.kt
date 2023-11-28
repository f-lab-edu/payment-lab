package org.collaborator.paymentlab.common.domain

import java.util.Collections

abstract class AbstractAggregateRoot<T> {
    private val events = mutableListOf<DomainEvent>()

    abstract fun id(): T?

    protected fun registerEvent(domainEvent: DomainEvent) {
        this.events.add(domainEvent)
    }

    fun pollAllEvents():List<DomainEvent> {
        return if (this.events.isNotEmpty()) {
            val domainEvents = events.toList()
            this.events.clear()
            domainEvents
        } else {
            Collections.emptyList()
        }
    }
}