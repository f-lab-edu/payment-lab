package org.collaborators.paymentslab.account.domain

import org.collaborator.paymentlab.common.Role

interface TokenGenerator {
    fun generate(email: String, roles: Set<Role>): Tokens
}