package org.collaborators.paymentslab.log.domain

interface EventResultRecorder<T> {
    fun record(event: T)
}