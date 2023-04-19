package org.collaborator.paymentlab.domain

import org.collaborator.paymentlab.domain.exception.DuplicatedEmailException
import org.collaborator.paymentlab.domain.exception.DuplicatedUsernameException

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
}