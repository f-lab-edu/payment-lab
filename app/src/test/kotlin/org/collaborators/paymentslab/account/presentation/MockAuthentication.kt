package org.collaborators.paymentslab.account.presentation

import org.collaborator.paymentlab.common.Role
import org.collaborators.paymentslab.account.domain.Account
import org.collaborators.paymentslab.account.presentation.request.RegisterAccountRequest
import org.collaborators.paymentslab.account.presentation.request.RegisterAdminAccountRequest

object MockAuthentication {
    val expiredAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sImVtYWlsIjoiaGVsbG9AZ21haWwuY29tIiwiaXNzIjoicGF5bWVudHNsYWIiLCJpYXQiOjE2ODUyNzE1OTIsImV4cCI6MTY4NTI3MzM5Mn0.92iGkI5ugZ4kJmOyThfoOGGGNSshWx5e0NCn0pMfexw"
    val expiredRefreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sImVtYWlsIjoiaGVsbG9AZ21haWwuY29tIiwiaXNzIjoicGF5bWVudHNsYWIiLCJpYXQiOjE2ODUyODA3OTcsImV4cCI6MTY4NTI4MjU5N30.t9-Qb27MxTleqWVzfqyBouFg7LWndt67WW7Eoi-WXzM"

    val testPlainPassword = "Qwer!234"
    val testEncrypyPassword = "\$2a\$10\$w5Idz2dN9FQFam1ZT3OoS.ZDlDom4dLfi6jQrukabcUHrGw8Ju49u"
    val testWrongPlainPassword = "wrong"
    val testWrongReissuerEmail = "nouser123@gmail.com"

    fun mockUserAccount(): Account {
        val account = Account.register(
            "hello2@gmail.com",
            testEncrypyPassword, "hello2", "010-1234-1234"
        )
        account.id = 1L
        account.emailVerified = true
        return account
    }

    fun mockAdminAccount(): Account {
        val account = Account.register(
            "hello2@gmail.com",
            testEncrypyPassword, "hello2", "010-1234-1234", hashSetOf(Role.USER, Role.ADMIN)
        )
        account.id = 1L
        account.emailVerified = true
        return account

    }

    fun mockWrongUserAccount(): Account {
        val account = Account.register(
            testWrongReissuerEmail,
            testEncrypyPassword, "hello2", "010-1234-1234"
        )
        account.id = 2L
        account.emailVerified = true
        return account
    }

    fun mockUserAccountFrom(registerAccountRequest: RegisterAccountRequest): Account {
        val userAccount = Account.register(
            registerAccountRequest.email,
            registerAccountRequest.password,
            registerAccountRequest.username,
            registerAccountRequest.phoneNumber
        )
        userAccount.id = 1L
        return userAccount
    }

    fun mockAdminAccountFrom(registerAccountRequest: RegisterAdminAccountRequest): Account {
        val userAccount = Account.register(
            registerAccountRequest.email,
            registerAccountRequest.password,
            registerAccountRequest.username,
            registerAccountRequest.phoneNumber
        )
        userAccount.id = 1L
        return userAccount
    }
}