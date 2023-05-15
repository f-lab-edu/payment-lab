package org.collaborators.paymentslab.account.domain

interface PasswordEncrypt {
    fun encode(rawPasswd: CharSequence): String
    fun matches(rawPasswd: CharSequence, encodedPasswd: String): Boolean
}