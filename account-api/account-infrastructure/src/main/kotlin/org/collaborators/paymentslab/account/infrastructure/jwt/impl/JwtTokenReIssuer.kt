package org.collaborators.paymentslab.account.infrastructure.jwt.impl

import org.collaborators.paymentslab.account.domain.AccountRepository
import org.collaborators.paymentslab.account.domain.TokenGenerator
import org.collaborators.paymentslab.account.domain.TokenReIssuer
import org.collaborators.paymentslab.account.domain.Tokens
import org.collaborators.paymentslab.account.infrastructure.jwt.RefreshTokenInfoRepository
import org.collaborators.paymentslab.account.infrastructure.jwt.Scopes
import org.collaborators.paymentslab.account.infrastructure.jwt.TokenExtractor
import org.collaborators.paymentslab.account.infrastructure.jwt.TokenParser
import org.collaborators.paymentslab.account.infrastructure.jwt.exception.RefreshTokenNotFoundException

class JwtTokenReIssuer(
    private val tokenGenerator: TokenGenerator,
    private val tokenExtractor: TokenExtractor,
    private val tokenParser: TokenParser,
    private val refreshTokenInfoRepository: RefreshTokenInfoRepository,
    private val accountRepository: AccountRepository
): TokenReIssuer {

    override fun reIssuance(payload: String): Tokens {
        val rawToken = tokenExtractor.extract(payload)
        val token = tokenParser.parse(rawToken!!)

        token.verify(Scopes.REFRESH_TOKEN.authority())

        if (!refreshTokenInfoRepository.existsByRefreshTokenJti(token.jti!!))
            throw RefreshTokenNotFoundException()

        val account = accountRepository.findByEmail(token.subject)
        val refreshTokenJti = refreshTokenInfoRepository.findByRefreshTokenJti(token.jti)
        refreshTokenJti.expire()
        return tokenGenerator.generate(account.email!!, account.roles)
    }
}