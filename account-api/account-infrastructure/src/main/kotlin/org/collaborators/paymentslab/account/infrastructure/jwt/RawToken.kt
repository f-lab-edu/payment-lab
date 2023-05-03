package org.collaborators.paymentslab.account.infrastructure.jwt

import org.collaborator.paymentlab.common.error.InvalidTokenException

class RawToken(
    val subject: String,
    val jti: String?,
    val authorities: List<String>
) {
    constructor(subject: String, authorities: List<String>): this(subject, null, authorities)

    fun verify(typeName: String) {
        if (!isRefreshable(typeName))
            throw InvalidTokenException()
    }

    private fun isRefreshable(typeName: String): Boolean {
        return authorities.stream().anyMatch { it.equals(typeName) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RawToken

        if (subject != other.subject) return false
        if (jti != other.jti) return false
        if (authorities != other.authorities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = subject.hashCode()
        result = 31 * result + jti.hashCode()
        result = 31 * result + authorities.hashCode()
        return result
    }


}