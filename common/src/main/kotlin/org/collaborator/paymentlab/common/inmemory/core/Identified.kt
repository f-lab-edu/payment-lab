package org.collaborator.paymentlab.common.inmemory.core

interface Identified<ID> {
    fun getId(): ID
}