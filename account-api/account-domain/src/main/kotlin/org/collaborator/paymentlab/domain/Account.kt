package org.collaborator.paymentlab.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import org.hibernate.annotations.UpdateTimestamp
import java.util.UUID
import kotlin.collections.HashSet

@Entity
@Table(name = "ACCOUNTS")
class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var accountKey: String? = null
    var email: String? = null
    var password: String? = null
    var username: String? = null
    var emailCheckToken: String? = null
    val emailCheckTokenGeneratedAt: LocalDateTime? = null
    val emailVerified: Boolean? = false
    @UpdateTimestamp
    val lastModifiedAt: LocalDateTime? = null
    val withdraw: Boolean? = false
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private var roles = HashSet<Role>()

    protected constructor()
    private constructor(email: String, password: String, username: String) {
        accountKey = ""
        this.email = email
        this.password = password
        this.username = username
        roles = HashSet()
    }

    companion object {
        fun register(email: String, encodedPassword: String, username: String): Account {
            val account = Account(email, encodedPassword, username)
            account.emailCheckToken = UUID.randomUUID().toString()
            return account
        }
    }

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