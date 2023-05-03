package org.collaborators.paymentslab.account.infrastructure.jpa

import org.collaborators.paymentslab.account.infrastructure.jwt.RefreshTokenInfo
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRefreshTokenInfoRepository: JpaRepository<RefreshTokenInfo, Long> {
    fun existsByRefreshTokenJti(jti: String): Boolean
    fun findByRefreshTokenJti(jti: String): RefreshTokenInfo
}