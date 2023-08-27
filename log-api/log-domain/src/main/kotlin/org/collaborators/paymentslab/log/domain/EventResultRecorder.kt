package org.collaborators.paymentslab.log.domain

interface EventResultRecorder<T> {
    fun execute(event: T)
}