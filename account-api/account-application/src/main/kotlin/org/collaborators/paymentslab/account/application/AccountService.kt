package org.collaborators.paymentslab.account.application

import org.collaborators.paymentslab.account.application.command.LoginAccount
import org.collaborators.paymentslab.account.application.command.RegisterAccount
import org.collaborators.paymentslab.account.application.command.RegisterConfirm
import org.collaborators.paymentslab.account.domain.*
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountService(
    private val accountRegister: AccountRegister,
    private val accountValidator: AccountValidator,
    private val accountLoginProcessor: AccountLoginProcessor,
    private val tokenGenerator: TokenGenerator,
    private val tokenReIssuer: TokenReIssuer,
    private val env: Environment
) {
    private val log = LoggerFactory.getLogger(AccountService::class.java)
    fun register(command: RegisterAccount)  {
        val account = accountRegister.register(command.email, command.passwd, command.username, command.phoneNumber)
        if (env.activeProfiles.contains("local") || env.activeProfiles.contains("test") || env.activeProfiles.contains("default")) { // TODO 이메일 인증 완료시, 해당 코드 삭제
            log.info("Account registered in test env. skipping email check.")
            accountRegister.registerConfirm(account.emailCheckToken!!, account.email)
        }
    }

    fun registerConfirm(command: RegisterConfirm): Boolean {
        val account = accountRegister.registerConfirm(command.token, command.email)
        return account.emailVerified
    }

    fun login(command: LoginAccount): TokenDto {
        val account = accountLoginProcessor.login(command.email, command.password)
        val tokens = tokenGenerator.generate(account.email, account.roles)
        return TokenDto(tokens.accessToken, tokens.refreshToken)
    }

    fun reIssuance(payload: String): TokenDto {
        val tokens = tokenReIssuer.reIssuance(payload)
        return TokenDto(tokens.accessToken, tokens.refreshToken)
    }

    fun validateWithPhoneNumber(phoneNumber: String) {
        accountValidator.validatePhoneNumber(phoneNumber)
    }

    data class TokenDto(val accessToken: String, val refreshToken: String)
}