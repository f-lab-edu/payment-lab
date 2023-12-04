package org.collaborators.paymentslab.account.infrastructure

import org.collaborator.paymentlab.common.inmemory.core.InMemoryRepository
import org.collaborators.paymentslab.account.domain.Account

class InMemoryAccountRepository: InMemoryRepository<Account, Long>(Long::class.java) {
    fun findByAccountKey(accountKey: String): Account {
        return findAll().first { it.accountKey == accountKey }
    }
}