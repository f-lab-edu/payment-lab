package org.collaborators.paymentslab.account.presentation.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)