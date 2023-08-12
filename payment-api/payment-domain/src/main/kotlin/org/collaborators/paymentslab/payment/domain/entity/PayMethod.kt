package org.collaborators.paymentslab.payment.domain.entity

import java.util.*

enum class PayMethod(
    val description: String
) {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    ACCOUNT_TRANSFER("계좌이체");

    open fun findByMethod(description: String): PayMethod? {
        return Arrays.stream(values())
            .filter { m: PayMethod -> m.description == description }
            .findFirst()
            .orElseThrow { IllegalArgumentException() }
    }
}