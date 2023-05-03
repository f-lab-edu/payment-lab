package org.collaborators.paymentslab.account.application

import org.collaborators.paymentslab.account.application.command.LoginAccount
import org.collaborators.paymentslab.account.domain.AccountRegister
import org.collaborators.paymentslab.account.application.command.RegisterAccount
import org.collaborators.paymentslab.account.application.command.RegisterConfirm
import org.collaborators.paymentslab.account.domain.AccountLoginProcessor
import org.collaborators.paymentslab.account.domain.TokenGenerator
import org.collaborators.paymentslab.account.domain.TokenReIssuer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountService(
    private val accountRegister: AccountRegister,
    private val accountLoginProcessor: AccountLoginProcessor,
    private val tokenGenerator: TokenGenerator,
    private val tokenReIssuer: TokenReIssuer
) {
    private val log = LoggerFactory.getLogger(AccountService::class.java)
    fun register(command: RegisterAccount): Boolean {
        val account = accountRegister.register(command.email, command.passwd, command.username)
        log.info("account email Token!! : ${account.emailCheckToken}")
        return account.id != null
    }

    fun registerConfirm(command: RegisterConfirm): Boolean {
        val account = accountRegister.registerConfirm(command.token, command.email)
        return account.emailVerified
    }

    fun login(command: LoginAccount): TokenDto {
        val account = accountLoginProcessor.login(command.email, command.password)
        val tokens = tokenGenerator.generate(account.email!!, account.roles)
        return TokenDto(tokens.accessToken, tokens.refreshToken)
    }

    fun reIssuance(payload: String): TokenDto {
        val tokens = tokenReIssuer.reIssuance(payload)
        return TokenDto(tokens.accessToken, tokens.refreshToken)
    }

    data class TokenDto(val accessToken: String, val refreshToken: String)
}