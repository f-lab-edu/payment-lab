package org.collaborators.paymentslab.account.domain

import jakarta.persistence.*
import org.collaborator.paymentlab.common.KeyGenerator
import org.collaborator.paymentlab.common.Role
import org.collaborator.paymentlab.common.domain.AbstractAggregateRoot
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "ACCOUNTS")
class Account protected constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var accountKey: String = KeyGenerator.generate("act_"),
    var email: String,
    var password: String,
    var username: String,
    var phoneNumber: String,
    var emailCheckToken: String? = null,
    var emailCheckTokenGeneratedAt: LocalDateTime? = null,
    var emailVerified: Boolean = false,
    var joinedAt: LocalDateTime? = null,
    @UpdateTimestamp
    val lastModifiedAt: LocalDateTime? = null,
    var withdraw: Boolean = false,
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = hashSetOf(Role.USER)
): AbstractAggregateRoot() {

    init {
        generateEmailCheckToken()
    }

    companion object {
        fun register(
            email: String, encodedPassword: String, username: String, phoneNumber: String,
            roles: MutableSet<Role> = hashSetOf(Role.USER)
        ) = Account(email = email, password = encodedPassword, username = username, phoneNumber = phoneNumber, roles = roles)
    }

    private fun generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString()
        this.emailCheckTokenGeneratedAt = LocalDateTime.now()
    }

    fun completeRegister() {
        this.emailVerified = true
        this.joinedAt = LocalDateTime.now()
    }

    fun isValidToken(token: String) = true
//    = token == emailCheckToken


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}