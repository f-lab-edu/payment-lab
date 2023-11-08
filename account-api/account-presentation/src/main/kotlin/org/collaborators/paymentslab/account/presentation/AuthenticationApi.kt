package org.collaborators.paymentslab.account.presentation

import jakarta.validation.Valid
import org.collaborator.paymentlab.common.V1_AUTH
import org.collaborator.paymentlab.common.result.ApiResult
import org.collaborators.paymentslab.account.application.AccountService
import org.collaborators.paymentslab.account.application.command.LoginAccount
import org.collaborators.paymentslab.account.application.command.RegisterAccount
import org.collaborators.paymentslab.account.application.command.RegisterAdminAccount
import org.collaborators.paymentslab.account.application.command.RegisterConfirm
import org.collaborators.paymentslab.account.presentation.request.LoginAccountRequest
import org.collaborators.paymentslab.account.presentation.request.RegisterAccountRequest
import org.collaborators.paymentslab.account.presentation.request.RegisterAdminAccountRequest
import org.collaborators.paymentslab.account.presentation.request.RegisterConfirmRequest
import org.collaborators.paymentslab.account.presentation.response.TokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping(V1_AUTH)
class AuthenticationApi(private val accountService: AccountService) {

    @Value("\${redirect.url.login}")
    private lateinit var redirectUrl: String

    @PostMapping("register/admin")
    fun registerAdmin(@RequestBody @Valid request: RegisterAdminAccountRequest) {
        accountService
            .registerAdmin(
                RegisterAdminAccount(
                    request.email, request.password, request.username, request.phoneNumber, request.adminKey
                )
            )
    }

    @PostMapping("register")
    fun register(@RequestBody @Valid request: RegisterAccountRequest) {
        accountService.register(RegisterAccount(request.email, request.password, request.username, request.phoneNumber))
    }

    @GetMapping("confirm")
    fun registerConfirm(@RequestBody @Valid request: RegisterConfirmRequest): ResponseEntity<Void> {
        accountService.registerConfirm(RegisterConfirm(request.token, request.email))

        val uri = URI.create(redirectUrl)
        val header = HttpHeaders()
        header.location = uri
        return ResponseEntity(header, HttpStatus.SEE_OTHER)
    }

    @PostMapping("login")
    fun login(@RequestBody @Valid request: LoginAccountRequest): ResponseEntity<ApiResult<TokenResponse>> {
        val tokens = accountService.login(LoginAccount(request.email, request.password))
        return ResponseEntity.ok(ApiResult.success(TokenResponse(tokens.accessToken, tokens.refreshToken)))
    }

    @PostMapping("reIssuance")
    fun reIssuance(@RequestHeader("Authorization") payload: String): ResponseEntity<ApiResult<TokenResponse>> {
        val tokens = accountService.reIssuance(payload)
        return ResponseEntity.ok(ApiResult.success(
            TokenResponse(tokens.accessToken, tokens.refreshToken)
        ))
    }
}