package org.collaborator.paymentlabs.domain

import org.collaborator.paymentlab.common.error.InvalidTokenException
import org.collaborator.paymentlabs.domain.exception.DuplicatedEmailException
import org.collaborator.paymentlabs.domain.exception.DuplicatedUsernameException

class AccountRegister(
    private val accountRepository: AccountRepository,
    private val encrypt: PasswordEncrypt
    ) {
    fun register(email: String, password: String, username: String): Account {
        if (accountRepository.existByEmail(email))
            throw DuplicatedEmailException()
        if (accountRepository.existByUsername(username))
            throw DuplicatedUsernameException()

        val account = Account.register(email, encrypt.encode(password), username)
        accountRepository.save(account)

        return account
    }

    fun registerConfirm(token: String, email: String): Account {
        val account = accountRepository.findByEmail(email)
        if (!account.isValidToken(token))
            throw InvalidTokenException()
        account.completeRegister()
        return account
    }
}