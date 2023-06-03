package org.collaborator.paymentlab.common

data class AuthenticatedUser(
    val id: Long,
    val accountKey: String,
    val roles: Set<Role>
)
