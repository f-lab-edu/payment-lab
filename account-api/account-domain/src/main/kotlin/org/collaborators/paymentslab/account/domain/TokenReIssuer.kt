package org.collaborators.paymentslab.account.domain

interface TokenReIssuer {
    fun reIssuance(payload: String): Tokens
}