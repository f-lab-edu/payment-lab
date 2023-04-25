package org.collaborators.paymentslab.account.application

import org.collaborator.paymentlabs.account.domain.AccountRegister
import org.collaborators.paymentslab.account.application.command.RegisterAccount
import org.collaborators.paymentslab.account.application.command.RegisterConfirm
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountService(
    private val accountRegister: AccountRegister
) {
    fun register(command: RegisterAccount): Boolean {
        val account = accountRegister.register(command.email, command.passwd, command.username)
        return account.id != null
    }

    fun registerConfirm(command: RegisterConfirm): Boolean {
        val account = accountRegister.registerConfirm(command.token, command.email)
        return account.emailVerified
    }
}