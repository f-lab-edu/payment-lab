package org.collaborator.paymentlab.common.inmemory.core

interface IdGenerator<T> {
    fun generateId(): T
}