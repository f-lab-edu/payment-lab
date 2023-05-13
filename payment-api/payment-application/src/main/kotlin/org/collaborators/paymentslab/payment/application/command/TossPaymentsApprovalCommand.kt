package org.collaborators.paymentslab.payment.application.command

data class TossPaymentsApprovalCommand(val orderId: String, val paymentKey: String, val amount: Int)
