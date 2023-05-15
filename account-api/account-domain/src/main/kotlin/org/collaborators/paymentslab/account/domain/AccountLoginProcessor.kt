package org.collaborators.paymentslab.account.domain

import org.collaborators.paymentslab.account.domain.exception.InvalidAccountException
import org.collaborators.paymentslab.account.domain.exception.PasswordNotMatchedException

class AccountLoginProcessor(
    private val accountRepository: AccountRepository,
    private val passwordEncrypt: PasswordEncrypt
) {
    fun login(email: String, rawPassword: String): Account {
        val account = accountRepository.findByEmail(email)
        if (!passwordEncrypt.matches(rawPassword, account.password!!))
            throw PasswordNotMatchedException()

        if (!account.emailVerified)
            throw InvalidAccountException("사용자의 이메일 인증이 아직 완료가 안되었습니다.")
        return account
    }
}