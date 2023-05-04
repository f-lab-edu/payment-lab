package org.collaborators.paymentslab.account.infrastructure.jwt.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.collaborator.paymentlab.common.Role
import org.collaborators.paymentslab.account.domain.TokenGenerator
import org.collaborators.paymentslab.account.domain.Tokens
import org.collaborators.paymentslab.account.infrastructure.jwt.RefreshTokenInfo
import org.collaborators.paymentslab.account.infrastructure.jwt.RefreshTokenInfoRepository
import org.collaborators.paymentslab.account.infrastructure.jwt.Scopes
import org.springframework.beans.factory.annotation.Value
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

class JwtTokenGenerator(
    private val refreshTokenInfoRepository: RefreshTokenInfoRepository,
): TokenGenerator {
    @Value("\${app.security.jwt.tokenExpirationSec}")
    private lateinit var tokenExpirationSec: String

    @Value("\${app.security.jwt.refreshExpirationSec}")
    private lateinit var refreshExpirationSec: String

    @Value("\${app.security.jwt.tokenIssuer}")
    private lateinit var tokenIssuer: String

    @Value("\${app.security.jwt.base64TokenSigningKey}")
    private lateinit var base64TokenSigningKey: String

    override fun generate(email: String, roles: Set<Role>): Tokens {
        return Tokens(generateAccessToken(email, roles), generateRefreshToken(email))
    }

    private fun generateAccessToken(email: String, roles: Set<Role>): String {
        val claims = HashMap<String, Any>()
        claims["email"] = email
        claims["scopes"] = getAuthorities(roles)
        val currentTime = LocalDateTime.now()
        val key = Keys.hmacShaKeyFor(base64TokenSigningKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(tokenIssuer)
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(currentTime.plusSeconds(tokenExpirationSec.toLong())
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getAuthorities(roles: Set<Role>): List<String> {
        return roles.map { "ROLE_${it.name}" }
    }

    private fun generateRefreshToken(email: String): String {
        val claims = HashMap<String, Any>()
        claims["email"] = email
        claims["scopes"] = listOf(Scopes.REFRESH_TOKEN.authority())
        val randomJti = UUID.randomUUID().toString()
        refreshTokenInfoRepository.save(RefreshTokenInfo(randomJti))
        val currentTime = LocalDateTime.now()
        val key = Keys.hmacShaKeyFor(base64TokenSigningKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(tokenIssuer)
            .setId(randomJti)
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(currentTime.plusSeconds(refreshExpirationSec.toLong())
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}