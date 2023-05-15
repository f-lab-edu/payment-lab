package org.collaborators.paymentslab.account.infrastructure.jwt

interface TokenParser {
    fun parse(token: String): RawToken
}