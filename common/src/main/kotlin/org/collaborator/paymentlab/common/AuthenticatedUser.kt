package org.collaborator.paymentlab.common

data class AuthenticatedUser(
    val id: String,
    val roles: Set<Role>
)
