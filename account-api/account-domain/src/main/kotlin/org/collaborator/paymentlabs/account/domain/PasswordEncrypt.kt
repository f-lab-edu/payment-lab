package org.collaborator.paymentlabs.account.domain

interface PasswordEncrypt {
    fun encode(rawPasswd: CharSequence): String
    fun matches(rawPasswd: CharSequence, encodedPasswd: String): Boolean
}