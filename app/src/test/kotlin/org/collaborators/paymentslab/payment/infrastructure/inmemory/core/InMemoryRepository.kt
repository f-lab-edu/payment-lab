package org.collaborators.paymentslab.payment.infrastructure.inmemory.core

import jakarta.persistence.Id
import org.collaborator.paymentlab.common.domain.AbstractAggregateRoot
import java.util.concurrent.ConcurrentHashMap

abstract class InMemoryRepository<T : AbstractAggregateRoot<ID>, ID>(idTypeParameter: Class<ID>) {
    private val storage: MutableMap<ID, T> = ConcurrentHashMap<ID, T>()
    private val idGenerator: IdGenerator<ID> = IdGeneratorFactory.create(idTypeParameter)

    fun findById(id: ID): T? {
        if (id == null) throw AssertionError()
        return storage[id]
    }

    fun save(entity: T): T {
        if (entity.id() == null) {
            val newId: ID = idGenerator.generateId()
            assignNewId(newId, entity)
        }
        storage[entity.id()!!] = entity
        return entity
    }

    private fun assignNewId(id: ID, entity: T) {
        for (field in entity::class.java.declaredFields) {
            if (field.isAnnotationPresent(Id::class.java)) {
                field.isAccessible = true
                try {
                    field[entity] = id
                } catch (e: IllegalAccessException) {
                    throw RuntimeException(e)
                }
            }
        }
    }
}