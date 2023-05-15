package org.collaborators.paymentslab.account.infrastructure.jwt

import jakarta.persistence.*
import org.collaborators.paymentslab.account.infrastructure.jwt.exception.AlreadyTokenExpiredException
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "REFRESH_TOKEN_INFO")
class RefreshTokenInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(unique = true, nullable = false)
    var refreshTokenJti: String? = null

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    var expired: Boolean? = false

    protected constructor()

    constructor(refreshTokenJti: String) {
        this.refreshTokenJti = refreshTokenJti
    }

    fun expire() {
        if (this.expired!!)
            throw AlreadyTokenExpiredException()
        this.expired = true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RefreshTokenInfo

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}