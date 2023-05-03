package org.collaborators.paymentslab.account.infrastructure.jwt.impl

import org.collaborators.paymentslab.account.infrastructure.jwt.TokenExtractor

class JwtTokenExtractor: TokenExtractor {

    private val HEADER_PREFIX = "Bearer "

    override fun extract(header: String?): String {
        return if (header.isNullOrEmpty()) {
            ""
        } else {
            header.substring(HEADER_PREFIX.length)
        }
    }
}