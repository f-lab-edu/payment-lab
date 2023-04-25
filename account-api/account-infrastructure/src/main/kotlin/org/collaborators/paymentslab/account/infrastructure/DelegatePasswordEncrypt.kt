package org.collaborators.paymentslab.account.infrastructure

import org.collaborator.paymentlabs.account.domain.PasswordEncrypt
import org.springframework.security.crypto.password.PasswordEncoder

class DelegatePasswordEncrypt(
    private val passwordEncoder: PasswordEncoder
): PasswordEncrypt {
    override fun encode(rawPasswd: CharSequence): String {
        return passwordEncoder.encode(rawPasswd)
    }

    override fun matches(rawPasswd: CharSequence, encodedPasswd: String): Boolean {
        return passwordEncoder.matches(rawPasswd, encodedPasswd)
    }
}