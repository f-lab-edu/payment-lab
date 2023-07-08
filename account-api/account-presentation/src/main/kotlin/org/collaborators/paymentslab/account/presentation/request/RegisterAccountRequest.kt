package org.collaborators.paymentslab.account.presentation.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.collaborators.paymentslab.account.presentation.validation.Password

data class RegisterAccountRequest(
    @field:Email @field:NotBlank
    val email: String,
    @field: NotBlank @field: Password
    val password: String,
    @field:NotBlank
    val username: String,
    @field:NotBlank @field: Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    val phoneNumber: String
)