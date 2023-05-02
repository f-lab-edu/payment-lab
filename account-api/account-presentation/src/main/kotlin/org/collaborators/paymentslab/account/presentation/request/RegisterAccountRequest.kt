package org.collaborators.paymentslab.account.presentation.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.collaborators.paymentslab.account.presentation.validation.Password

data class RegisterAccountRequest(
    @field:Email @field:NotBlank
    val email: String,
    @field: NotBlank @field: Password
    val password: String,
    @field:NotBlank
    val username: String
)