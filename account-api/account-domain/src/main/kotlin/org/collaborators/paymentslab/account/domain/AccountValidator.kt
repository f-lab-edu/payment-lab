package org.collaborators.paymentslab.account.domain

import org.collaborators.paymentslab.account.domain.exception.InvalidAdminKeyException
import org.collaborators.paymentslab.account.domain.exception.InvalidPhoneNumberException

class AccountValidator(
    private val accountRepository: AccountRepository,
) {
    fun validate(phoneNumber: String) {
        if (!accountRepository.existByPhoneNumber(phoneNumber))
            throw InvalidPhoneNumberException()
    }

    fun validate(adminKey: String, clientAdminKey: String) {
        if (adminKey != clientAdminKey)
            throw InvalidAdminKeyException()
    }
}