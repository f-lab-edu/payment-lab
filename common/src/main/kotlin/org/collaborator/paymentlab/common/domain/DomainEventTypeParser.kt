package org.collaborator.paymentlab.common.domain

object DomainEventTypeParser {
    fun parseSimpleName(domainEventString: String, classType: Class<*>) = StringBuilder()
        .append(domainEventString)
        .append(",")
        .append(classType.simpleName)
        .toString()
}