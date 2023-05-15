package org.collaborators.paymentslab.account.presentation.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterConfirmRequest(
    @field:NotBlank
    val token: String,

    @field:NotBlank @field:Email
    val email: String
)
