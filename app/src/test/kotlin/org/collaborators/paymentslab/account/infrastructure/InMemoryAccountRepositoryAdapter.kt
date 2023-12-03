package org.collaborators.paymentslab.account.infrastructure

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ResourceNotFoundException
import org.collaborators.paymentslab.account.domain.Account
import org.collaborators.paymentslab.account.domain.AccountRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.security.auth.login.AccountNotFoundException

@Repository
@Profile(value = ["test"])
class InMemoryAccountRepositoryAdapter(
    private val inMemoryAccountRepository: InMemoryAccountRepository
): AccountRepository {
    override fun existByEmail(email: String): Boolean {
        return inMemoryAccountRepository.findAll().any { it.email == email }
    }

    override fun existByPhoneNumber(phoneNumber: String): Boolean {
        return inMemoryAccountRepository.findAll().any { it.phoneNumber == phoneNumber }
    }

    override fun save(account: Account): Account {
        return inMemoryAccountRepository.save(account)
    }

    override fun findByEmail(email: String): Account {
        if (!existByEmail(email))
            throw ResourceNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)
        return inMemoryAccountRepository.findAll().first { it.email == email }
    }

    override fun findById(accountId: String): Account {
        return inMemoryAccountRepository.findByAccountKey(accountId)
    }

    override fun existByUsername(username: String): Boolean {
        return inMemoryAccountRepository.findAll().any { it.username == username }
    }
}