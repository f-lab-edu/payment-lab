package org.collaborator.paymentlabs.account.domain

import java.lang.IllegalArgumentException
import java.util.*

enum class Role {
    USER,
    VIP_USER,
    ADMIN;

    companion object {
        fun findByRole(role: String): Role {
            return Arrays.stream(Role.values())
                .filter { r -> r.name.equals(role) }
                .findFirst()
                .orElseThrow { IllegalArgumentException() }
        }
    }
}