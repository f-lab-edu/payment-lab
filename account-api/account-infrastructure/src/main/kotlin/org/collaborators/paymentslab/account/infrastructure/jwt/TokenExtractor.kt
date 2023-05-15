package org.collaborators.paymentslab.account.infrastructure.jwt

interface TokenExtractor {
    fun extract(payload: String?): String?
}