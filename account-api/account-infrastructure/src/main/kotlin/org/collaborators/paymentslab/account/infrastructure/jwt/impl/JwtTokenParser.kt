package org.collaborators.paymentslab.account.infrastructure.jwt.impl

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.collaborator.paymentlab.common.error.InvalidTokenException
import org.collaborators.paymentslab.account.infrastructure.jwt.RawToken
import org.collaborators.paymentslab.account.infrastructure.jwt.TokenParser
import org.collaborators.paymentslab.account.infrastructure.jwt.exception.AlreadyTokenExpiredException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import java.nio.charset.StandardCharsets
import java.security.Key

class JwtTokenParser: TokenParser {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${app.security.jwt.base64TokenSigningKey}")
    private lateinit var base64TokenSigningKey: String

    override fun parse(token: String): RawToken {
        try {
            val jwt: Jws<Claims> = Jwts.parserBuilder()
                .setSigningKey(getKey(base64TokenSigningKey))
                .build()
                .parseClaimsJws(token)
            val jti = jwt.body["jti"] as String?
                ?: return RawToken(jwt.body["email"] as String, jwt.body["scopes"] as List<String>)
            return RawToken(jwt.body["email"] as String, jti, jwt.body["scopes"] as List<String>)
        } catch (ex: UnsupportedJwtException) {
            log.error("Invalid JWT Token", ex)
            throw InvalidTokenException()
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT Token", ex)
            throw InvalidTokenException()
        } catch (ex: IllegalArgumentException) {
            log.error("Invalid JWT Token", ex)
            throw InvalidTokenException()
        } catch (expiredEx: ExpiredJwtException) {
            log.info("JWT Token is expired", expiredEx)
            throw AlreadyTokenExpiredException()
        }
    }

    private fun getKey(signKey: String): Key? {
        return Keys.hmacShaKeyFor(signKey.toByteArray(StandardCharsets.UTF_8))
    }
}