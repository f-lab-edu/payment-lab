package org.collaborators.paymentslab.account.infrastructure.jwt

interface RefreshTokenInfoRepository {
    fun save(entity: RefreshTokenInfo): RefreshTokenInfo

    fun existsByRefreshTokenJti(jti: String): Boolean

    fun findByRefreshTokenJti(jti: String): RefreshTokenInfo
}