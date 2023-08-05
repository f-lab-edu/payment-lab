package org.collaborators.paymentslab.account.presentation.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class AccountPhoneNumberRequest(
    @field:NotBlank @field: Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    val phoneNumber: String
)
