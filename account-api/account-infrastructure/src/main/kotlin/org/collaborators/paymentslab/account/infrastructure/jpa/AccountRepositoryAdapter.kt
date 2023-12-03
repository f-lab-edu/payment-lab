package org.collaborators.paymentslab.account.infrastructure.jpa

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ResourceNotFoundException
import org.collaborators.paymentslab.account.domain.Account
import org.collaborators.paymentslab.account.domain.AccountRepository
import org.springframework.context.annotation.Profile

@Profile(value = ["!test"])
class AccountRepositoryAdapter(
    private val jpaAccountRepository: JpaAccountRepository
): AccountRepository {
    override fun existByEmail(email: String): Boolean {
        return jpaAccountRepository.existsByEmail(email)
    }

    override fun existByPhoneNumber(phoneNumber: String): Boolean {
        return jpaAccountRepository.existsByPhoneNumber(phoneNumber)
    }

    override fun save(account: Account): Account {
        return jpaAccountRepository.save(account)
    }

    override fun findByEmail(email: String): Account {
        return jpaAccountRepository.findByEmail(email) ?: throw ResourceNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)
    }

    override fun findById(accountId: String): Account {
        return jpaAccountRepository.findByAccountKey(accountId) ?: throw ResourceNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)
    }

    override fun existByUsername(username: String): Boolean {
        return jpaAccountRepository.existsByUsername(username)
    }
}