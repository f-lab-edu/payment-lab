package org.collaborators.paymentslab.account.application.command

data class RegisterAdminAccount(
    val email: String,
    val passwd: String,
    val username: String,
    val phoneNumber: String,
    val adminKey: String
)
