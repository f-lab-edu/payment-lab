package org.collaborators.paymentslab.account.domain

interface TokenGenerator {
    fun generate(email: String, roles: Set<Role>): Tokens
}