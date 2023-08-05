package org.collaborators.paymentslab.account.presentation

import jakarta.validation.Valid
import org.collaborators.paymentslab.account.application.AccountService
import org.collaborators.paymentslab.account.presentation.request.AccountPhoneNumberRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/account")
class AccountApi(private val accountService: AccountService) {

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("phone")
    fun validateAccountByPhoneNumber(@RequestBody @Valid request: AccountPhoneNumberRequest) {
        accountService.validateWithPhoneNumber(request.phoneNumber)
    }

}