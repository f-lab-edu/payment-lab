package org.collaborators.paymentslab.payment.domain

interface PaymentOrderProcessor {
    fun process(accountId: Long, orderName: String, amount: Int)
}