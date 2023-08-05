package org.collaborators.paymentslab.account.infrastructure

import org.collaborators.paymentslab.account.domain.AccountLoginProcessor
import org.collaborators.paymentslab.account.domain.AccountRegister
import org.collaborators.paymentslab.account.domain.AccountValidator
import org.collaborators.paymentslab.account.infrastructure.jpa.AccountRepositoryAdapter
import org.collaborators.paymentslab.account.infrastructure.jpa.RefreshTokenInfoRepositoryAdapter
import org.collaborators.paymentslab.account.infrastructure.jwt.impl.JwtTokenExtractor
import org.collaborators.paymentslab.account.infrastructure.jwt.impl.JwtTokenGenerator
import org.collaborators.paymentslab.account.infrastructure.jwt.impl.JwtTokenParser
import org.collaborators.paymentslab.account.infrastructure.jwt.impl.JwtTokenReIssuer
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(
    value = [
        AccountRepositoryAdapter::class,
        DelegatePasswordEncrypt::class,
        AccountRegister::class,
        AccountValidator::class,
        AccountLoginProcessor::class,
        JwtTokenGenerator::class,
        JwtTokenParser::class,
        JwtTokenExtractor::class,
        JwtTokenReIssuer::class,
        RefreshTokenInfoRepositoryAdapter::class
    ]
)
@Configuration
class AccountModuleConfiguration {
}