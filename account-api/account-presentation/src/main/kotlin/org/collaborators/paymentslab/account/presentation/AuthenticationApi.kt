package org.collaborators.paymentslab.account.presentation

import jakarta.validation.Valid
import org.collaborators.paymentslab.account.application.AccountService
import org.collaborators.paymentslab.account.application.command.RegisterAccount
import org.collaborators.paymentslab.account.presentation.request.RegisterAccountRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthenticationApi(private val accountService: AccountService) {

    @PostMapping("register")
    fun register(@RequestBody @Valid request: RegisterAccountRequest) {
        accountService.register(RegisterAccount(request.email, request.password, request.username))
    }
}