package org.collaborators.paymentslab.account.presentation

import jakarta.validation.Valid
import org.collaborators.paymentslab.account.application.AccountService
import org.collaborators.paymentslab.account.application.command.RegisterAccount
import org.collaborators.paymentslab.account.application.command.RegisterConfirm
import org.collaborators.paymentslab.account.presentation.request.RegisterAccountRequest
import org.collaborators.paymentslab.account.presentation.request.RegisterConfirmRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("api/v1/auth")
class AuthenticationApi(private val accountService: AccountService) {

    @PostMapping("register")
    fun register(@RequestBody @Valid request: RegisterAccountRequest) {
        accountService.register(RegisterAccount(request.email, request.password, request.username))
    }

    @GetMapping("confirm")
    fun registerConfirm(@RequestBody @Valid request: RegisterConfirmRequest): ResponseEntity<Void> {
        accountService.registerConfirm(RegisterConfirm(request.token, request.email))

        val uri = URI.create("http://localhost:3000/login")
        val header = HttpHeaders()
        header.location = uri
        return ResponseEntity(header, HttpStatus.SEE_OTHER)
    }
}