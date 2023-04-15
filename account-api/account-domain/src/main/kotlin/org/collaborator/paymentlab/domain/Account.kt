package org.collaborator.paymentlab.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "ACCOUNTS")
class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val accountKey: String,
    val email: String,
    val password: String,
    val username: String,
    val emailCheckToken: String,
    val emailCheckTokenGeneratedAt: LocalDateTime,
    val emailVerified: Boolean,
    @UpdateTimestamp
    val lastModifiedAt: LocalDateTime,
    val withdraw: Boolean,
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val roles: Set<Role> = HashSet()
)