package org.collaborators.paymentslab.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborator.paymentlab.common.Role
import org.collaborators.paymentslab.account.domain.AccountRepository
import org.collaborators.paymentslab.account.infrastructure.jwt.TokenExtractor
import org.collaborators.paymentslab.account.infrastructure.jwt.TokenParser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter(
    private val accountRepository: AccountRepository,
    private val tokenExtractor: TokenExtractor,
    private val tokenParser: TokenParser
): OncePerRequestFilter() {
    private val AUTHORIZATION_HEADER = "Authorization"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(AUTHORIZATION_HEADER)
        val rawToken = tokenExtractor.extract(header)
        if (!rawToken.isNullOrBlank()) {
            val tokenMap = tokenParser.parse(rawToken)
            val email = tokenMap.subject
            val account = accountRepository.findByEmail(email)

            if (SecurityContextHolder.getContext().authentication == null) {
                val context = UsernamePasswordAuthenticationToken(
                    AuthenticatedUser(account.id!!, account.accountKey!!, account.roles), null, authorities(account.roles)
                )
                SecurityContextHolder.getContext().authentication = context
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun authorities(roles: Set<Role>): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }.toList()
    }
}