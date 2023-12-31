package org.collaborators.paymentslab.account.domain

import org.collaborator.paymentlab.common.Role
import org.collaborator.paymentlab.common.error.InvalidTokenException
import org.collaborators.paymentslab.account.domain.exception.DuplicatedEmailException

class AccountRegister(
    private val accountRepository: AccountRepository,
    private val encrypt: PasswordEncrypt
    ) {
    fun register(email: String, password: String, username: String, phoneNumber: String, roles: MutableSet<Role> = hashSetOf(
        Role.USER)): Account {
        if (accountRepository.existByEmail(email))
            throw DuplicatedEmailException()

        val account = Account.register(email, encrypt.encode(password), username, phoneNumber, roles)
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