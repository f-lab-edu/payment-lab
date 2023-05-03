package org.collaborators.paymentslab.account.infrastructure.jpa

import org.collaborators.paymentslab.account.infrastructure.jwt.RefreshTokenInfo
import org.collaborators.paymentslab.account.infrastructure.jwt.RefreshTokenInfoRepository

class RefreshTokenInfoRepositoryAdapter(
    private val jpaRefreshTokenInfoRepository: JpaRefreshTokenInfoRepository): RefreshTokenInfoRepository {
    override fun save(entity: RefreshTokenInfo): RefreshTokenInfo {
        return jpaRefreshTokenInfoRepository.save(entity)
    }

    override fun existsByRefreshTokenJti(jti: String): Boolean {
        return jpaRefreshTokenInfoRepository.existsByRefreshTokenJti(jti)
    }

    override fun findByRefreshTokenJti(jti: String): RefreshTokenInfo {
        return jpaRefreshTokenInfoRepository.findByRefreshTokenJti(jti)
    }
}