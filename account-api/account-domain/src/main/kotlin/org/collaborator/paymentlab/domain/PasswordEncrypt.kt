package org.collaborator.paymentlab.domain

interface PasswordEncrypt {
    fun encode(rawPasswd: CharSequence): String
    fun matches(rawPasswd: CharSequence, encodedPasswd: String): Boolean
}