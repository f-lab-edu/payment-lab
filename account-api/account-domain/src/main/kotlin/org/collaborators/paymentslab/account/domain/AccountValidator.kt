package org.collaborators.paymentslab.account.domain

import org.collaborators.paymentslab.account.domain.exception.InvalidPhoneNumberException

class AccountValidator(
    private val accountRepository: AccountRepository,
) {
    fun validatePhoneNumber(phoneNumber: String) {
        if (!accountRepository.existByPhoneNumber(phoneNumber))
            throw InvalidPhoneNumberException()
    }
}