package org.collaborators.paymentslab.account.infrastructure.jpa

import org.collaborators.paymentslab.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

interface JpaAccountRepository: JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account?

    fun existsByEmail(email: String): Boolean

    fun existsByUsername(username: String): Boolean

    fun existsByPhoneNumber(phoneNumber: String): Boolean

    fun findByAccountKey(id: String): Account?
}