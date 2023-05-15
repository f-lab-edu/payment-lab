package org.collaborators.paymentslab.account.presentation.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.collaborators.paymentslab.account.presentation.validation.Password

data class LoginAccountRequest(
    @field:NotBlank @field:Email
    val email: String,

    @field:NotBlank @field:Password
    val password: String
)
