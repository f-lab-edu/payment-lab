package org.collaborator.paymentlabs.domain

interface PasswordEncrypt {
    fun encode(rawPasswd: CharSequence): String
    fun matches(rawPasswd: CharSequence, encodedPasswd: String): Boolean
}