package org.collaborator.paymentlab.common

import org.collaborator.paymentlab.common.domain.DomainEvent
import org.reflections.Reflections

object DomainEventTypeMap {
    private val domainEventTypeMap: Map<String, Class<out DomainEvent>> = Reflections("org.collaborators.paymentslab")
        .getSubTypesOf(DomainEvent::class.java)
        .mapNotNull {
                clazz -> Pair(clazz.simpleName, clazz)
        }.toMap()

    fun typeFrom(eventTypeMap: Map<String, String>): Class<out DomainEvent> {
        val simpleName = eventTypeMap["typeSimpleName"]
        return domainEventTypeMap[simpleName] ?: throw IllegalArgumentException("invalid domain event type")
    }
}