package org.collaborators.paymentslab.account.application.command

data class RegisterAccount(
    val email: String,
    val password: String,
    val username: String,
    val phoneNumber: String
) {
    override fun toString(): String {
        return "RegisterAccount(email='$email', password='[PROTECTED]', username='$username', phoneNumber='$phoneNumber')"
    }
}
