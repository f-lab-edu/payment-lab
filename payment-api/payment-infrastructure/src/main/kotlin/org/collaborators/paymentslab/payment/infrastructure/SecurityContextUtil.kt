package org.collaborators.paymentslab.payment.infrastructure

import org.collaborator.paymentlab.common.AuthenticatedUser
import org.springframework.security.core.context.SecurityContextHolder

fun getCurrentAccount(): AuthenticatedUser {
    return SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
}