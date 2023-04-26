package org.collaborators.paymentslab.account.infrastructure

import org.collaborators.paymentslab.account.domain.AccountRegister
import org.collaborators.paymentslab.account.infrastructure.jpa.AccountRepositoryAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(
    value = [
        AccountRepositoryAdapter::class,
        DelegatePasswordEncrypt::class,
        AccountRegister::class
    ]
)
@Configuration
class AccountModuleConfiguration {
}