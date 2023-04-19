package org.collaborator.paymentlab.domain

interface AccountRepository {
    fun existByEmail(email: String): Boolean
    fun save(account: Account): Account
    fun findByEmail(email: String): Account
    fun findById(accountId: String): Account
    fun existByUsername(username: String): Boolean
}